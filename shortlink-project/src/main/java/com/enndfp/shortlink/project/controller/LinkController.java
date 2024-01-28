package com.enndfp.shortlink.project.controller;

import com.enndfp.shortlink.project.service.LinkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接控制层
 *
 * @author Enndfp
 */
@RestController
@RequestMapping("/link")
public class LinkController {

    @Resource
    private LinkService linkService;
}
