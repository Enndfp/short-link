package com.enndfp.shortlink.admin.controller;

import com.enndfp.shortlink.admin.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.admin.common.convention.result.Result;
import com.enndfp.shortlink.admin.dto.req.group.GroupAddReqDTO;
import com.enndfp.shortlink.admin.service.GroupService;
import com.enndfp.shortlink.admin.utils.ResultUtil;
import com.enndfp.shortlink.admin.utils.ThrowUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
