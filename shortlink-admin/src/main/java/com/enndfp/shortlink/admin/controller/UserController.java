package com.enndfp.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.enndfp.shortlink.admin.common.convention.result.Result;
import com.enndfp.shortlink.admin.common.convention.result.Results;
import com.enndfp.shortlink.admin.dto.req.UserLoginReqDTO;
import com.enndfp.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.enndfp.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.enndfp.shortlink.admin.dto.resp.UserActualRespDTO;
import com.enndfp.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.enndfp.shortlink.admin.dto.resp.UserRespDTO;
import com.enndfp.shortlink.admin.service.UserService;
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
    @GetMapping("{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {
        return Results.success(userService.getUserByUsername(username));
    }

    /**
     * 根据用户名查询无脱敏用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/actual/{username}")
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username) {
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username), UserActualRespDTO.class));
    }

    /**
     * 根据用户名判断用户是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    @GetMapping("/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username) {
        return Results.success(userService.hasUsername(username));
    }

    /**
     * 用户注册
     *
     * @param userRegisterReqDTO 用户注册请求实体
     * @return 注册结果
     */
    @PostMapping()
    public Result<Void> register(@RequestBody UserRegisterReqDTO userRegisterReqDTO) {
        userService.register(userRegisterReqDTO);
        return Results.success();
    }

    /**
     * 用户修改
     */
    @PutMapping()
    public Result<Void> update(@RequestBody UserUpdateReqDTO userUpdateReqDTO) {
        userService.update(userUpdateReqDTO);
        return Results.success();
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO userLoginReqDTO) {
        return Results.success(userService.login(userLoginReqDTO));
    }

    /**
     * 检查用户是否登录
     */
    @GetMapping("/check-login")
    public Result<Boolean> checkLogin(@RequestParam("username") String username, @RequestParam("token") String token) {
        return Results.success(userService.checkLogin(username, token));
    }
}
