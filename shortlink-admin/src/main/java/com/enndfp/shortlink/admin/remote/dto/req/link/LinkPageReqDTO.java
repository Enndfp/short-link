package com.enndfp.shortlink.admin.remote.dto.req.link;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * 短链接分页请求传输对象
 *
 * @author Enndfp
 */
@Data
public class LinkPageReqDTO extends Page {

    /**
     * 分组标识
     */
    private String gid;
}
