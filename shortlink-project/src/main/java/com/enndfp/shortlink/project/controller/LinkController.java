package com.enndfp.shortlink.project.controller;

import com.enndfp.shortlink.project.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.project.common.convention.result.Result;
import com.enndfp.shortlink.project.dto.req.link.LinkCreateReqDTO;
import com.enndfp.shortlink.project.dto.resp.link.LinkCreateRespDTO;
import com.enndfp.shortlink.project.service.LinkService;
import com.enndfp.shortlink.project.utils.ResultUtil;
import com.enndfp.shortlink.project.utils.ThrowUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 创建短链接
     *
     * @param linkCreateReqDTO 短链接创建请求传输对象
     * @return 短链接创建响应传输对象
     */
    @PostMapping("/create")
    public Result<LinkCreateRespDTO> create(@RequestBody LinkCreateReqDTO linkCreateReqDTO) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(linkCreateReqDTO == null, ErrorCode.CLIENT_ERROR);
        // 2. 执行创建短链接逻辑
        LinkCreateRespDTO linkCreateRespDTO = linkService.create(linkCreateReqDTO);

        return ResultUtil.success(linkCreateRespDTO);
    }
}
