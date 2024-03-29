package com.enndfp.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enndfp.shortlink.admin.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.admin.context.UserContext;
import com.enndfp.shortlink.admin.dao.entity.GroupDO;
import com.enndfp.shortlink.admin.dao.mapper.GroupMapper;
import com.enndfp.shortlink.admin.dto.req.group.GroupAddReqDTO;
import com.enndfp.shortlink.admin.dto.req.group.GroupSortReqDTO;
import com.enndfp.shortlink.admin.dto.req.group.GroupUpdateReqDTO;
import com.enndfp.shortlink.admin.dto.resp.group.GroupRespDTO;
import com.enndfp.shortlink.admin.remote.dto.LinkRemoteService;
import com.enndfp.shortlink.admin.remote.dto.resp.link.LinkCountRespDTO;
import com.enndfp.shortlink.admin.service.GroupService;
import com.enndfp.shortlink.admin.utils.RandomStringUtil;
import com.enndfp.shortlink.admin.utils.ThrowUtil;
import jakarta.annotation.Resource;
import org.redisson.api.RBloomFilter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短链接分组业务层实现类
 *
 * @author Enndfp
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private RBloomFilter<String> gidGenerateCachePenetrationBloomFilter;

    /**
     * TODO: 后续重构为 SpringCloud Feign 调用
     */
    LinkRemoteService linkRemoteService = new LinkRemoteService() {
    };

    @Override
    public void add(GroupAddReqDTO groupAddReqDTO) {
        // 1. 校验请求参数
        String groupName = groupAddReqDTO.getGroupName();
        ThrowUtil.throwClientIf(StrUtil.isBlank(groupName), ErrorCode.GROUP_NAME_NULL);

        // 2. 生成的分组id不能重复
        String gid;
        do {
            gid = RandomStringUtil.generateRandomString(6);
        } while (checkGidExist(gid));

        // 3. 添加分组
        GroupDO groupDO = new GroupDO();
        groupDO.setGid(gid);
        groupDO.setUsername(UserContext.getUsername());
        groupDO.setGroupName(groupName);
        int insert = groupMapper.insert(groupDO);
        ThrowUtil.throwServerIf(insert != 1, ErrorCode.GROUP_SAVE_ERROR);

        // 4. 将gid添加到布隆过滤器中
        gidGenerateCachePenetrationBloomFilter.add(gid);
    }

    @Override
    public List<GroupRespDTO> listGroup() {
        // 1. 从上下文中获取用户名
        String username = UserContext.getUsername();

        // 2. 构造查询条件
        LambdaQueryWrapper<GroupDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupDO::getUsername, username);
        queryWrapper.orderByDesc(GroupDO::getSortOrder);
        queryWrapper.orderByDesc(GroupDO::getUpdatedTime);

        // 3. 查询分组列表
        List<GroupDO> groupDOList = groupMapper.selectList(queryWrapper);
        // 3.1 查询分组下的链接数量
        List<LinkCountRespDTO> linkCountRespDTOList = linkRemoteService.count(groupDOList.stream().map(GroupDO::getGid).toList()).getData();
        // 3.2 拷贝分组列表
        List<GroupRespDTO> groupRespDTOList = BeanUtil.copyToList(groupDOList, GroupRespDTO.class);
        // 3.3 设置分组下的链接数量
        groupRespDTOList.forEach(groupRespDTO -> {
            for (LinkCountRespDTO linkCountRespDTO : linkCountRespDTOList) {
                if (groupRespDTO.getGid().equals(linkCountRespDTO.getGid())) {
                    groupRespDTO.setLinkCount(linkCountRespDTO.getLinkCount());
                    break;
                }
            }
        });

        return groupRespDTOList;
    }

    @Override
    public void update(GroupUpdateReqDTO groupUpdateReqDTO) {
        // 1. 校验请求参数
        String gid = groupUpdateReqDTO.getGid();
        String groupName = groupUpdateReqDTO.getGroupName();
        ThrowUtil.throwClientIf(StrUtil.hasBlank(gid, groupName), ErrorCode.CLIENT_ERROR);

        // 2. 构造修改条件
        LambdaUpdateWrapper<GroupDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(GroupDO::getGid, gid);
        updateWrapper.eq(GroupDO::getUsername, UserContext.getUsername());

        // 3. 修改分组
        GroupDO groupDO = new GroupDO();
        groupDO.setGroupName(groupName);
        int update = groupMapper.update(groupDO, updateWrapper);
        ThrowUtil.throwServerIf(update != 1, ErrorCode.GROUP_UPDATE_ERROR);
    }

    @Override
    public void delete(String gid) {
        // 1. 构造删除条件
        LambdaUpdateWrapper<GroupDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(GroupDO::getGid, gid);
        updateWrapper.eq(GroupDO::getUsername, UserContext.getUsername());

        // 3. 删除分组
        int delete = groupMapper.delete(updateWrapper);
        ThrowUtil.throwServerIf(delete != 1, ErrorCode.GROUP_DELETE_ERROR);
    }

    @Override
    public void sort(List<GroupSortReqDTO> groupSortReqDTOList) {
        for (GroupSortReqDTO groupSortReqDTO : groupSortReqDTOList) {
            // 1. 校验请求参数
            String gid = groupSortReqDTO.getGid();
            Integer sortOrder = groupSortReqDTO.getSortOrder();
            ThrowUtil.throwClientIf(StrUtil.hasBlank(gid) || sortOrder == null, ErrorCode.CLIENT_ERROR);

            // 2. 构造排序修改条件
            LambdaUpdateWrapper<GroupDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(GroupDO::getGid, gid);
            updateWrapper.eq(GroupDO::getUsername, UserContext.getUsername());

            // 3. 修改排序分组
            GroupDO groupDO = new GroupDO();
            groupDO.setSortOrder(sortOrder);
            int update = groupMapper.update(groupDO, updateWrapper);
            ThrowUtil.throwServerIf(update != 1, ErrorCode.GROUP_SORT_ERROR);
        }
    }

    @Override
    public void addDefaultGroup(String username) {
        // 1. 生成的分组id不能重复
        String gid;
        do {
            gid = RandomStringUtil.generateRandomString(6);
        } while (checkGidExist(gid));

        // 2. 添加默认分组
        GroupDO groupDO = new GroupDO();
        groupDO.setGid(gid);
        groupDO.setUsername(username);
        groupDO.setGroupName("默认分组");
        int insert = groupMapper.insert(groupDO);
        ThrowUtil.throwServerIf(insert != 1, ErrorCode.GROUP_SAVE_ERROR);

        // 3. 将gid添加到布隆过滤器中
        gidGenerateCachePenetrationBloomFilter.add(gid);
    }

    /**
     * 校验Gid是否存在
     *
     * @param gid 分组ID
     * @return 是否存在
     */
    private boolean checkGidExist(String gid) {
        // 判断布隆过滤器是否存在此gid
        return gidGenerateCachePenetrationBloomFilter.contains(gid);
    }
}




