package com.enndfp.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.enndfp.shortlink.admin.dao.entity.UserDO;
import com.enndfp.shortlink.admin.dto.req.UserLoginReqDTO;
import com.enndfp.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.enndfp.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.enndfp.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.enndfp.shortlink.admin.dto.resp.UserRespDTO;

/**
 * 用户业务层接口
 *
 * @author Enndfp
 */
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserRespDTO getUserByUsername(String username);

    /**
     * 根据用户名判断用户是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    Boolean hasUsername(String username);

    /**
     * 用户注册
     *
     * @param userRegisterReqDTO 用户注册请求实体
     */
    void register(UserRegisterReqDTO userRegisterReqDTO);

    /**
     * 用户修改
     *
     * @param userUpdateReqDTO 用户修改请求实体
     */
    void update(UserUpdateReqDTO userUpdateReqDTO);

    /**
     * 用户登录
     *
     * @param userLoginReqDTO 用户登录请求实体
     * @return 登录结果
     */
    UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO);

    /**
     * 检查用户是否登录
     *
     * @param username 用户名
     * @param token    token
     * @return 是否登录
     */
    Boolean checkLogin(String username, String token);

    /**
     * 用户登出
     * @param username 用户名
     * @param token token
     */
    void logout(String username, String token);
}
