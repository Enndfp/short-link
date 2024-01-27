package com.enndfp.shortlink.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.enndfp.shortlink.admin.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.admin.common.convention.result.Result;
import com.enndfp.shortlink.admin.dto.req.group.GroupAddReqDTO;
import com.enndfp.shortlink.admin.dto.req.group.GroupSortReqDTO;
import com.enndfp.shortlink.admin.dto.req.group.GroupUpdateReqDTO;
import com.enndfp.shortlink.admin.dto.resp.group.GroupRespDTO;
import com.enndfp.shortlink.admin.service.GroupService;
import com.enndfp.shortlink.admin.utils.ResultUtil;
import com.enndfp.shortlink.admin.utils.ThrowUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接分组控制层
 *
 * @author Enndfp
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Resource
    private GroupService groupService;

    /**
     * 添加分组
     *
     * @param groupAddReqDTO 分组添加请求数据传输对象
     * @return 添加结果
     */
    @PostMapping("/add")
    public Result<Void> add(@RequestBody GroupAddReqDTO groupAddReqDTO) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(groupAddReqDTO == null, ErrorCode.CLIENT_ERROR);
        // 2. 添加分组
        groupService.add(groupAddReqDTO);

        return ResultUtil.success();
    }

    /**
     * 查询分组列表
     *
     * @return 分组列表
     */
    @GetMapping("/list")
    public Result<List<GroupRespDTO>> list() {
        // 1. 查询分组列表
        List<GroupRespDTO> groupRespDTOList = groupService.listGroup();

        return ResultUtil.success(groupRespDTOList);
    }

    /**
     * 修改分组
     *
     * @param groupUpdateReqDTO 分组修改请求数据传输对象
     * @return 修改结果
     */
    @PostMapping("/update")
    public Result<Void> update(@RequestBody GroupUpdateReqDTO groupUpdateReqDTO) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(groupUpdateReqDTO == null, ErrorCode.CLIENT_ERROR);
        // 2. 修改分组
        groupService.update(groupUpdateReqDTO);

        return ResultUtil.success();
    }

    /**
     * 删除分组
     *
     * @param gid 分组标识
     * @return 删除结果
     */
    @PostMapping("/delete")
    public Result<Void> delete(@RequestParam String gid) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(StrUtil.isBlank(gid), ErrorCode.CLIENT_ERROR);
        // 2. 删除分组
        groupService.delete(gid);

        return ResultUtil.success();
    }

    /**
     * 排序分组
     *
     * @param groupSortReqDTOList 分组排序请求数据传输对象列表
     * @return 排序结果
     */
    @PostMapping("sort")
    public Result<Void> sort(@RequestBody List<GroupSortReqDTO> groupSortReqDTOList) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(groupSortReqDTOList == null, ErrorCode.CLIENT_ERROR);
        // 2. 排序分组
        groupService.sort(groupSortReqDTOList);

        return ResultUtil.success();
    }
}
