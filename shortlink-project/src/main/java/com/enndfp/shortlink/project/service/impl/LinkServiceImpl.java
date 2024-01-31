package com.enndfp.shortlink.project.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enndfp.shortlink.project.common.convention.errorcode.ErrorCode;
import com.enndfp.shortlink.project.common.convention.exception.ServerException;
import com.enndfp.shortlink.project.dao.entity.LinkDO;
import com.enndfp.shortlink.project.dao.mapper.LinkMapper;
import com.enndfp.shortlink.project.dto.req.link.LinkCreateReqDTO;
import com.enndfp.shortlink.project.dto.req.link.LinkPageReqDTO;
import com.enndfp.shortlink.project.dto.resp.link.LinkCreateRespDTO;
import com.enndfp.shortlink.project.dto.resp.link.LinkPageRespDTO;
import com.enndfp.shortlink.project.service.LinkService;
import com.enndfp.shortlink.project.utils.HashUtil;
import com.enndfp.shortlink.project.utils.ThrowUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * 短链接业务层实现类
 *
 * @author Enndfp
 */
@Slf4j
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, LinkDO> implements LinkService {

    @Resource
    private LinkMapper linkMapper;
    @Resource
    private RBloomFilter<String> shortUriCreateCachePenetrationBloomFilter;

    @Override
    public LinkCreateRespDTO create(LinkCreateReqDTO linkCreateReqDTO) {
        // 1. 拷贝请求参数到持久层实体类
        LinkDO linkDO = BeanUtil.toBean(linkCreateReqDTO, LinkDO.class);

        // 2. 生成短链接后缀
        String linkSuffix = generateSuffix(linkCreateReqDTO);

        // 3. 设置持久层实体类属性
        String fullShortUrl = linkCreateReqDTO.getDomain() + "/" + linkSuffix;
        linkDO.setFullShortUrl(fullShortUrl);
        linkDO.setShortUri(linkSuffix);

        // 4. 创建短链接
        try {
            linkMapper.insert(linkDO);
        } catch (DuplicateKeyException ex) {
            log.warn("短链接已存在，短链接：{}", fullShortUrl);
            throw new ServerException(ErrorCode.LINK_CREATE_ERROR);
        } finally {
            // 抛不抛异常都要加到布隆过滤器，抛异常说明并发访问布隆过滤器不存在则加入进去，不抛异常说明短链接加入数据库，也要加入进去
            shortUriCreateCachePenetrationBloomFilter.add(fullShortUrl);
        }

        return BeanUtil.toBean(linkDO, LinkCreateRespDTO.class);
    }

    @Override
    public IPage<LinkPageRespDTO> pageLink(LinkPageReqDTO linkPageReqDTO) {
        // 1. 校验请求参数
        String gid = linkPageReqDTO.getGid();
        ThrowUtil.throwClientIf(StrUtil.isBlank(gid), ErrorCode.GROUP_ID_NULL);

        // 2. 构造分页查询参数
        LambdaQueryWrapper<LinkDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LinkDO::getGid, gid);
        queryWrapper.eq(LinkDO::getEnableStatus, 1);
        queryWrapper.orderByDesc(LinkDO::getCreatedTime);

        // 3. 执行分页查询
        IPage<LinkDO> linkPage = linkMapper.selectPage(linkPageReqDTO, queryWrapper);
        return linkPage.convert(linkDO -> BeanUtil.toBean(linkDO, LinkPageRespDTO.class));
    }


    /**
     * 生成短链接后缀
     *
     * @param linkCreateReqDTO 短链接创建请求传输对象
     * @return 短链接后缀
     */
    private String generateSuffix(LinkCreateReqDTO linkCreateReqDTO) {
        // 1. 校验请求参数
        String originUrl = linkCreateReqDTO.getOriginUrl();
        String domain = linkCreateReqDTO.getDomain();
        ThrowUtil.throwClientIf(StrUtil.hasBlank(originUrl, domain), ErrorCode.ORIGIN_URL_NULL);

        // 2. 自定义生成次数，防止生成短链接时，短链接已存在，导致死循环
        int customGenerateCount = 0;
        String shortUri;
        while (true) {
            // 如果生成次数超过10次，则抛出服务器频繁创建链接的错误
            ThrowUtil.throwServerIf(customGenerateCount > 10, ErrorCode.LINK_FREQUENT_CREATE);

            // 3. 加上时间戳，防止短链接重复
            originUrl += System.currentTimeMillis();
            shortUri = HashUtil.hashToBase62(originUrl);
            String fullShortUrl = domain + "/" + shortUri;

            // 4. 如果短链接不存在，则跳出循环
            if (!shortUriCreateCachePenetrationBloomFilter.contains(fullShortUrl)) {
                break;
            }
            customGenerateCount++;
        }
        return shortUri;
    }
}




