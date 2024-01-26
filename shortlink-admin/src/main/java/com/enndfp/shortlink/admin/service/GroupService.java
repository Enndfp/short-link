package com.enndfp.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.enndfp.shortlink.admin.dao.entity.GroupDO;
import com.enndfp.shortlink.admin.dto.req.group.GroupAddReqDTO;
import com.enndfp.shortlink.admin.dto.resp.group.GroupRespDTO;

import java.util.List;

/**
 * 短链接分组业务层接口
 *
 * @author Enndfp
 */
public interface GroupService extends IService<GroupDO> {

    /**
     * 添加分组
     *
     * @param groupAddReqDTO 分组添加请求数据传输对象
     */
    void add(GroupAddReqDTO groupAddReqDTO);

    /**
     * 查询分组列表
     *
     * @return 分组列表
     */
    List<GroupRespDTO> listGroup();
}
