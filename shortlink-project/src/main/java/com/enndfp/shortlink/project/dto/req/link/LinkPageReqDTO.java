package com.enndfp.shortlink.project.dto.req.link;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enndfp.shortlink.project.dao.entity.LinkDO;
import lombok.Data;

/**
 * 短链接分页请求传输对象
 *
 * @author Enndfp
 */
@Data
public class LinkPageReqDTO extends Page<LinkDO> {

    /**
     * 分组标识
     */
    private String gid;
}
