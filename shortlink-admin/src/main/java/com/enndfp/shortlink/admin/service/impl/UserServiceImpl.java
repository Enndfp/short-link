package com.enndfp.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enndfp.shortlink.admin.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.admin.dao.entity.UserDO;
import com.enndfp.shortlink.admin.dao.mapper.UserMapper;
import com.enndfp.shortlink.admin.dto.req.user.UserLoginReqDTO;
import com.enndfp.shortlink.admin.dto.req.user.UserRegisterReqDTO;
import com.enndfp.shortlink.admin.dto.req.user.UserUpdateReqDTO;
import com.enndfp.shortlink.admin.dto.resp.user.UserActualRespDTO;
import com.enndfp.shortlink.admin.dto.resp.user.UserLoginRespDTO;
import com.enndfp.shortlink.admin.dto.resp.user.UserRespDTO;
import com.enndfp.shortlink.admin.service.UserService;
import com.enndfp.shortlink.admin.utils.ThrowUtil;
import jakarta.annotation.Resource;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.enndfp.shortlink.admin.common.constant.RedisCacheConstant.*;

/**
 * 用户业务层实现类
 *
 * @author Enndfp
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

    @Override
    public UserRespDTO getUserByUsername(String username) {
        // 1. 构造查询条件并查询
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getUsername, username);
        UserDO userDO = userMapper.selectOne(queryWrapper);

        // 2. 校验用户是否存在
        ThrowUtil.throwServerIf(userDO == null, ErrorCode.USER_NOT_EXIST);

        return BeanUtil.toBean(userDO, UserRespDTO.class);
    }

    @Override
    public UserActualRespDTO getActualUserByUsername(String username) {
        // 1. 构造查询条件并查询
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getUsername, username);
        UserDO userDO = userMapper.selectOne(queryWrapper);

        // 2. 校验用户是否存在
        ThrowUtil.throwServerIf(userDO == null, ErrorCode.USER_NOT_EXIST);

        return BeanUtil.toBean(userDO, UserActualRespDTO.class);
    }

    @Override
    public Boolean checkUsername(String username) {
        // 判断布隆过滤器是否存在此用户名
        return userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO userRegisterReqDTO) {
        // 1. 校验用户名是否存在
        String username = userRegisterReqDTO.getUsername();
        ThrowUtil.throwClientIf(username == null, ErrorCode.USER_NAME_NULL);
        ThrowUtil.throwClientIf(checkUsername(username), ErrorCode.USER_NAME_EXIST);

        // 2. 给用户名上锁，防止并发注册
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + username);
        try {
            // 2.1 尝试获取锁
            ThrowUtil.throwClientIf(!lock.tryLock(), ErrorCode.USER_NAME_EXIST);

            // 3. 保存用户信息
            UserDO userDO = BeanUtil.toBean(userRegisterReqDTO, UserDO.class);
            int insert = userMapper.insert(userDO);
            ThrowUtil.throwServerIf(insert != 1, ErrorCode.USER_REGISTER_ERROR);

            // 4. 将用户名存入布隆过滤器
            userRegisterCachePenetrationBloomFilter.add(username);
        } finally {
            // 5. 释放锁
            lock.unlock();
        }
    }

    @Override
    public void update(UserUpdateReqDTO userUpdateReqDTO) {
        // TODO 验证当前用户是否为登录用户
        // 1. 校验用户名是否存在
        String username = userUpdateReqDTO.getUsername();
        ThrowUtil.throwClientIf(username == null, ErrorCode.USER_NAME_NULL);

        // 2. 构造更新条件
        LambdaUpdateWrapper<UserDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserDO::getUsername, username);
        UserDO userDO = BeanUtil.toBean(userUpdateReqDTO, UserDO.class);

        // 3. 执行更新
        int update = userMapper.update(userDO, updateWrapper);
        ThrowUtil.throwServerIf(update != 1, ErrorCode.USER_INFO_UPDATE_ERROR);
    }

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO) {
        // 1. 校验用户名和密码
        String username = userLoginReqDTO.getUsername();
        String password = userLoginReqDTO.getPassword();
        ThrowUtil.throwClientIf(username == null, ErrorCode.USER_NAME_NULL);
        ThrowUtil.throwClientIf(password == null, ErrorCode.PASSWORD_NULL);

        // 2. 校验用户是否存在
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getUsername, username);
        queryWrapper.eq(UserDO::getPassword, password);
        UserDO userDO = userMapper.selectOne(queryWrapper);
        ThrowUtil.throwServerIf(userDO == null, ErrorCode.USER_NOT_EXIST);

        // 3. 校验用户是否已登录
        Boolean hasLogin = stringRedisTemplate.hasKey(USER_LOGIN_KEY + username);
        ThrowUtil.throwServerIf(Boolean.TRUE.equals(hasLogin), ErrorCode.USER_HAS_LOGIN);

        // 4. 生成token并存入redis
        String token = UUID.randomUUID().toString(true);
        stringRedisTemplate.opsForHash().put(USER_LOGIN_KEY + username, token, JSONUtil.toJsonStr(userDO));
        stringRedisTemplate.expire(USER_LOGIN_KEY + username, USER_LOGIN_TTL, TimeUnit.DAYS);

        return new UserLoginRespDTO(token);
    }

    @Override
    public Boolean checkLogin(String username, String token) {
        // 检查redis中是否存在token
        return stringRedisTemplate.opsForHash().get(USER_LOGIN_KEY + username, token) != null;
    }

    @Override
    public void logout(String username, String token) {
        // 1. 判断用户是否登录
        ThrowUtil.throwServerIf(!checkLogin(username, token), ErrorCode.USER_HAS_LOGIN);

        // 2. 删除redis中的token
        stringRedisTemplate.opsForHash().delete(USER_LOGIN_KEY + username, token);
    }
}
