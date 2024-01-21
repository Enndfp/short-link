package com.enndfp.shortlink.controller;

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

    /**
     * 根据用户名查询用户信息
     */
    @GetMapping("{username}")
    public String getUserByUsername(@PathVariable("username") String username) {
        return "Hi " + username;
    }
}
