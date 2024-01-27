package com.enndfp.shortlink.admin.dto.req.group;

import lombok.Data;

/**
 * 短链接分组修改请求数据传输对象
 *
 * @author Enndfp
 */
@Data
public class GroupUpdateReqDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名称
     */
    private String groupName;
}
