package com.enndfp.shortlink.admin.controller;

import com.enndfp.shortlink.admin.common.convention.result.Result;
import com.enndfp.shortlink.admin.common.convention.result.Results;
import com.enndfp.shortlink.admin.dto.resp.UserRespDTO;
import com.enndfp.shortlink.admin.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     */
    @GetMapping("{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username) {
        return Results.success(userService.getUserByUsername(username));
    }
}
