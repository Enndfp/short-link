package com.enndfp.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.enndfp.shortlink.project.dao.entity.LinkDO;
import com.enndfp.shortlink.project.dto.req.link.LinkCreateReqDTO;
import com.enndfp.shortlink.project.dto.req.link.LinkPageReqDTO;
import com.enndfp.shortlink.project.dto.resp.link.LinkCountRespDTO;
import com.enndfp.shortlink.project.dto.resp.link.LinkCreateRespDTO;
import com.enndfp.shortlink.project.dto.resp.link.LinkPageRespDTO;

import java.util.List;

/**
 * 短链接业务层接口
 *
 * @author Enndfp
 */
public interface LinkService extends IService<LinkDO> {

    /**
     * 创建短链接
     *
     * @param linkCreateReqDTO 短链接创建请求传输对象
     * @return 短链接创建响应传输对象
     */
    LinkCreateRespDTO create(LinkCreateReqDTO linkCreateReqDTO);

    /**
     * 分页查询短链接
     *
     * @param linkPageReqDTO 短链接分页请求传输对象
     * @return 短链接分页响应传输对象
     */
    IPage<LinkPageRespDTO> pageLink(LinkPageReqDTO linkPageReqDTO);

    /**
     * 根据分组标识统计链接数量
     *
     * @param gids 分组标识列表
     * @return 链接数量返回数据传输对象列表
     */
    List<LinkCountRespDTO> countByGids(List<String> gids);
}
