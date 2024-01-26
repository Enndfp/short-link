package com.enndfp.shortlink.admin.dto.resp.group;

import lombok.Data;

/**
 * 分组信息返回实体
 *
 * @author Enndfp
 */
@Data
public class GroupRespDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 创建分组用户名
     */
    private String username;

    /**
     * 分组排序
     */
    private Integer sortOrder;
}
