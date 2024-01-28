package com.enndfp.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enndfp.shortlink.project.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.project.dao.entity.LinkDO;
import com.enndfp.shortlink.project.dao.mapper.LinkMapper;
import com.enndfp.shortlink.project.dto.req.link.LinkCreateReqDTO;
import com.enndfp.shortlink.project.dto.resp.link.LinkCreateRespDTO;
import com.enndfp.shortlink.project.service.LinkService;
import com.enndfp.shortlink.project.utils.HashUtil;
import com.enndfp.shortlink.project.utils.ThrowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 短链接业务层实现类
 *
 * @author Enndfp
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, LinkDO> implements LinkService {

    @Resource
    private LinkMapper linkMapper;

    @Override
    public LinkCreateRespDTO create(LinkCreateReqDTO linkCreateReqDTO) {
        // 1. 拷贝请求参数到持久层实体类
        LinkDO linkDO = BeanUtil.toBean(linkCreateReqDTO, LinkDO.class);

        // 2. 生成短链接后缀
        String originUrl = linkCreateReqDTO.getOriginUrl();
        String linkSuffix = generateSuffix(originUrl);

        // 3. 设置持久层实体类属性
        linkDO.setFullShortUrl(linkCreateReqDTO.getDomain() + "/" + linkSuffix);
        linkDO.setShortUri(linkSuffix);

        // 4. 创建短链接
        int insert = linkMapper.insert(linkDO);
        ThrowUtil.throwServerIf(insert != 1, ErrorCode.LINK_CREATE_ERROR);

        return BeanUtil.toBean(linkDO, LinkCreateRespDTO.class);
    }

    /**
     * 生成短链接后缀
     *
     * @param originUrl 原始链接
     * @return 短链接后缀
     */
    private String generateSuffix(String originUrl) {
        // 1. 校验请求参数
        ThrowUtil.throwClientIf(StrUtil.isBlank(originUrl), ErrorCode.ORIGIN_URL_NULL);

        // 2. 执行生成短链接后缀逻辑
        return HashUtil.hashToBase62(originUrl);
    }
}




