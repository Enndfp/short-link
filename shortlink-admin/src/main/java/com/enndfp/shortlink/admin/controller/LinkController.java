package com.enndfp.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.enndfp.shortlink.admin.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.admin.common.convention.result.Result;
import com.enndfp.shortlink.admin.remote.dto.LinkRemoteService;
import com.enndfp.shortlink.admin.remote.dto.req.link.LinkPageReqDTO;
import com.enndfp.shortlink.admin.remote.dto.resp.link.LinkPageRespDTO;
import com.enndfp.shortlink.admin.remote.dto.req.link.LinkCreateReqDTO;
import com.enndfp.shortlink.admin.remote.dto.resp.link.LinkCreateRespDTO;
import com.enndfp.shortlink.admin.utils.ThrowUtil;
import org.springframework.web.bind.annotation.*;

/**
 * 短链接控制层
 *
 * @author Enndfp
 */
@RestController
@RequestMapping("/link")
public class LinkController {

    /**
     * TODO: 后续重构为 SpringCloud Feign 调用
     */
    LinkRemoteService linkRemoteService = new LinkRemoteService() {
    };

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
        return linkRemoteService.create(linkCreateReqDTO);
    }

    /**
     * 分页查询短链接
     *
     * @param linkPageReqDTO 短链接分页请求传输对象
     * @return 短链接分页响应传输对象
     */
    @GetMapping("/page")
    public Result<IPage<LinkPageRespDTO>> page(@ModelAttribute LinkPageReqDTO linkPageReqDTO) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(linkPageReqDTO == null, ErrorCode.CLIENT_ERROR);
        // 2. 执行分页查询短链接逻辑
        return linkRemoteService.pageLink(linkPageReqDTO);
    }
}
