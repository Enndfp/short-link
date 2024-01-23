package com.enndfp.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enndfp.shortlink.admin.common.convention.exception.ClientException;
import com.enndfp.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.enndfp.shortlink.admin.dao.entity.UserDO;
import com.enndfp.shortlink.admin.dao.mapper.UserMapper;
import com.enndfp.shortlink.admin.dto.req.UserLoginReqDTO;
import com.enndfp.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.enndfp.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.enndfp.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.enndfp.shortlink.admin.dto.resp.UserRespDTO;
import com.enndfp.shortlink.admin.service.UserService;
import jakarta.annotation.Resource;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.enndfp.shortlink.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;

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
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getUsername, username);
        UserDO userDO = userMapper.selectOne(queryWrapper);
        UserRespDTO result = new UserRespDTO();
        BeanUtils.copyProperties(userDO, result);
        return result;
    }

    @Override
    public Boolean hasUsername(String username) {
        return userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO userRegisterReqDTO) {
        if (hasUsername(userRegisterReqDTO.getUsername())) {
            throw new ClientException(UserErrorCodeEnum.USER_NAME_EXIST);
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + userRegisterReqDTO.getUsername());
        try {
            if (lock.tryLock()) {
                int inserted = userMapper.insert(BeanUtil.toBean(userRegisterReqDTO, UserDO.class));
                if (inserted != 1) {
                    throw new ClientException(UserErrorCodeEnum.USER_SAVE_ERROR);
                }
                userRegisterCachePenetrationBloomFilter.add(userRegisterReqDTO.getUsername());
                return;
            }
            throw new ClientException(UserErrorCodeEnum.USER_NAME_EXIST);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void update(UserUpdateReqDTO userUpdateReqDTO) {
        // TODO 验证当前用户是否为登录用户
        LambdaUpdateWrapper<UserDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserDO::getUsername, userUpdateReqDTO.getUsername());
        userMapper.update(BeanUtil.toBean(userUpdateReqDTO, UserDO.class), updateWrapper);
    }

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getUsername, userLoginReqDTO.getUsername());
        queryWrapper.eq(UserDO::getPassword, userLoginReqDTO.getPassword());
        queryWrapper.eq(UserDO::getDelFlag, 0);
        UserDO userDO = userMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ClientException("用户不存在");
        }
        Boolean hasLogin = stringRedisTemplate.hasKey("login_" + userLoginReqDTO.getUsername());
        if (hasLogin != null && hasLogin) {
            throw new ClientException("用户已登录");
        }
        String uuid = UUID.randomUUID(true).toString();
        stringRedisTemplate.opsForHash().put("login_" + userLoginReqDTO.getUsername(), uuid, JSONUtil.toJsonStr(userDO));
        stringRedisTemplate.expire("login_" + userLoginReqDTO.getUsername(), 30, TimeUnit.MINUTES);
        return new UserLoginRespDTO(uuid);
    }

    @Override
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get("login_" + username, token) != null;
    }
}
