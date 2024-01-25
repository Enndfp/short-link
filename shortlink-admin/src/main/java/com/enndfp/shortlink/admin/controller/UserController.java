package com.enndfp.shortlink.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.enndfp.shortlink.admin.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.admin.common.convention.result.Result;
import com.enndfp.shortlink.admin.dto.req.user.UserLoginReqDTO;
import com.enndfp.shortlink.admin.dto.req.user.UserRegisterReqDTO;
import com.enndfp.shortlink.admin.dto.req.user.UserUpdateReqDTO;
import com.enndfp.shortlink.admin.dto.resp.user.UserActualRespDTO;
import com.enndfp.shortlink.admin.dto.resp.user.UserLoginRespDTO;
import com.enndfp.shortlink.admin.dto.resp.user.UserRespDTO;
import com.enndfp.shortlink.admin.service.UserService;
import com.enndfp.shortlink.admin.utils.ResultUtil;
import com.enndfp.shortlink.admin.utils.ThrowUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制层
 *
 * @author Enndfp
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/query")
    public Result<UserRespDTO> getUserByUsername(@RequestParam("username") String username) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(username == null, ErrorCode.USER_NAME_NULL);
        // 2. 查询用户信息
        UserRespDTO userRespDTO = userService.getUserByUsername(username);

        return ResultUtil.success(userRespDTO);
    }

    /**
     * 根据用户名查询无脱敏用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/query-actual")
    public Result<UserActualRespDTO> getActualUserByUsername(@RequestParam("username") String username) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(username == null, ErrorCode.USER_NAME_NULL);
        // 2. 查询用户信息
        UserActualRespDTO userActualRespDTO = userService.getActualUserByUsername(username);

        return ResultUtil.success(userActualRespDTO);
    }

    /**
     * 根据用户名判断用户是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    @GetMapping("/check-username")
    public Result<Boolean> checkUserExistByUsername(@RequestParam("username") String username) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(username == null, ErrorCode.USER_NAME_NULL);
        // 2. 执行检查逻辑
        return ResultUtil.success(userService.checkUsername(username));
    }

    /**
     * 用户注册
     *
     * @param userRegisterReqDTO 用户注册请求实体
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody UserRegisterReqDTO userRegisterReqDTO) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(userRegisterReqDTO == null, ErrorCode.CLIENT_ERROR);
        // 2. 处理注册逻辑
        userService.register(userRegisterReqDTO);

        return ResultUtil.success();
    }

    /**
     * 用户修改
     *
     * @param userUpdateReqDTO 用户修改请求实体
     * @return 修改结果
     */
    @PostMapping("/update")
    public Result<Void> update(@RequestBody UserUpdateReqDTO userUpdateReqDTO) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(userUpdateReqDTO == null, ErrorCode.CLIENT_ERROR);
        // 2. 处理修改逻辑
        userService.update(userUpdateReqDTO);

        return ResultUtil.success();
    }

    /**
     * 用户登录
     *
     * @param userLoginReqDTO 用户登录请求实体
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO userLoginReqDTO) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(userLoginReqDTO == null, ErrorCode.CLIENT_ERROR);
        // 2. 处理登录逻辑
        UserLoginRespDTO userLoginRespDTO = userService.login(userLoginReqDTO);

        return ResultUtil.success(userLoginRespDTO);
    }

    /**
     * 检查用户是否登录
     *
     * @param username 用户名
     * @param token    token
     * @return 是否登录
     */
    @GetMapping("/check-login")
    public Result<Boolean> checkLogin(@RequestParam("username") String username, @RequestParam("token") String token) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(StrUtil.hasBlank(username, token), ErrorCode.USER_OR_TOKEN_NULL);
        // 2. 执行检查逻辑
        Boolean checkLogin = userService.checkLogin(username, token);

        return ResultUtil.success(checkLogin);
    }

    /**
     * 用户登出
     *
     * @param username 用户名
     * @param token    token
     * @return 登出结果
     */
    @PostMapping("/logout")
    public Result<Void> logout(@RequestParam("username") String username, @RequestParam("token") String token) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(StrUtil.hasBlank(username, token), ErrorCode.USER_OR_TOKEN_NULL);
        // 2. 处理登出逻辑
        userService.logout(username, token);

        return ResultUtil.success();
    }
}
