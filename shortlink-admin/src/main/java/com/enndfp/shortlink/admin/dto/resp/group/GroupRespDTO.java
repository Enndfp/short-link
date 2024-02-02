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
     * 分组排序
     */
    private Integer sortOrder;

    /**
     * 分组下的链接数量
     */
    private Integer linkCount;
}
