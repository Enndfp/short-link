package com.enndfp.shortlink.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.enndfp.shortlink.project.dao.entity.LinkDO;
import com.enndfp.shortlink.project.dao.mapper.LinkMapper;
import com.enndfp.shortlink.project.service.LinkService;
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
}




