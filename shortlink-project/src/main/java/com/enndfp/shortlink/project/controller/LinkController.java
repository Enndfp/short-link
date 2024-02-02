package com.enndfp.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.enndfp.shortlink.project.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.project.common.convention.result.Result;
import com.enndfp.shortlink.project.dto.req.link.LinkCreateReqDTO;
import com.enndfp.shortlink.project.dto.req.link.LinkPageReqDTO;
import com.enndfp.shortlink.project.dto.resp.link.LinkCountRespDTO;
import com.enndfp.shortlink.project.dto.resp.link.LinkCreateRespDTO;
import com.enndfp.shortlink.project.dto.resp.link.LinkPageRespDTO;
import com.enndfp.shortlink.project.service.LinkService;
import com.enndfp.shortlink.project.utils.ResultUtil;
import com.enndfp.shortlink.project.utils.ThrowUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        IPage<LinkPageRespDTO> linkPageRespDTO = linkService.pageLink(linkPageReqDTO);

        return ResultUtil.success(linkPageRespDTO);
    }

    /**
     * 统计分组下短链接数量
     *
     * @param gids 分组标识列表
     * @return 分组下的链接数量
     */
    @GetMapping("/count")
    public Result<List<LinkCountRespDTO>> count(@RequestParam List<String> gids) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(gids == null || gids.isEmpty(), ErrorCode.CLIENT_ERROR);
        // 2. 统计分组下短链接数量
        List<LinkCountRespDTO> linkCountRespDTOList = linkService.countByGids(gids);

        return ResultUtil.success(linkCountRespDTOList);
    }
}
