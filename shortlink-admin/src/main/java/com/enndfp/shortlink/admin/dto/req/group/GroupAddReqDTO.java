package com.enndfp.shortlink.admin.dto.req.group;

import lombok.Data;

/**
 * 短链接分组添加请求数据传输对象
 *
 * @author Enndfp
 */
@Data
public class GroupAddReqDTO {

    /**
     * 分组名称
     */
    private String groupName;
}
