package com.enndfp.shortlink.admin.dto.req.group;

import lombok.Data;

/**
 * 短链接分组排序请求数据传输对象
 *
 * @author Enndfp
 */
@Data
public class GroupSortReqDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 排序
     */
    private Integer sortOrder;
}
