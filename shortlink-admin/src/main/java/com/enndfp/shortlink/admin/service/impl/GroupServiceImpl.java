package com.enndfp.shortlink.admin.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enndfp.shortlink.admin.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.admin.dao.entity.GroupDO;
import com.enndfp.shortlink.admin.dao.mapper.GroupMapper;
import com.enndfp.shortlink.admin.dto.req.group.GroupAddReqDTO;
import com.enndfp.shortlink.admin.service.GroupService;
import com.enndfp.shortlink.admin.utils.ThrowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 短链接分组业务层实现类
 *
 * @author Enndfp
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    @Resource
    private GroupMapper groupMapper;

    @Override
    public void add(GroupAddReqDTO groupAddReqDTO) {
        // 1. 校验请求参数
        String groupName = groupAddReqDTO.getGroupName();
        ThrowUtil.throwClientIf(StrUtil.isBlank(groupName), ErrorCode.GROUP_NULL);

        // 2. 同一用户生成的分组id不能重复
        String gid;
        do {
            gid = RandomUtil.randomString(6);
        } while (checkGidExist(gid));

        // 3. 添加分组
        GroupDO groupDO = new GroupDO();
        groupDO.setGid(gid);
        // TODO: 从上下文中获取用户名
        groupDO.setUsername("enndfp");
        groupDO.setGroupName(groupName);
        int insert = groupMapper.insert(groupDO);
        ThrowUtil.throwServerIf(insert != 1, ErrorCode.GROUP_SAVE_ERROR);
    }

    /**
     * 校验Gid是否存在
     *
     * @param gid 分组ID
     * @return 是否存在
     */
    private boolean checkGidExist(String gid) {
        // 1. 构造查询条件
        LambdaQueryWrapper<GroupDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupDO::getGid, gid);
        // TODO: 从上下文中获取用户名
        queryWrapper.eq(GroupDO::getUsername, "enndfp");

        // 2. 查询分组数量
        Long count = groupMapper.selectCount(queryWrapper);

        return count > 1;
    }
}




