package com.enndfp.shortlink.project.dto.resp.link;

import lombok.Data;

/**
 * 链接数量返回数据传输对象
 *
 * @author Enndfp
 */
@Data
public class LinkCountRespDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组下的链接数量
     */
    private String linkCount;
}
