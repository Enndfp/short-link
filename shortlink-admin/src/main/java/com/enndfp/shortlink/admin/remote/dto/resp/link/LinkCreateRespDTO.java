package com.enndfp.shortlink.admin.remote.dto.resp.link;

import lombok.Data;

/**
 * 短链接创建响应传输对象
 *
 * @author Enndfp
 */
@Data
public class LinkCreateRespDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 原始链接
     */
    private String originUrl;
}
