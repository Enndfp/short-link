package com.enndfp.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.enndfp.shortlink.admin.dao.entity.UserDO;
import com.enndfp.shortlink.admin.dto.resp.UserRespDTO;

/**
 * 用户业务层接口
 *
 * @author Enndfp
 */
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserRespDTO getUserByUsername(String username);
}
