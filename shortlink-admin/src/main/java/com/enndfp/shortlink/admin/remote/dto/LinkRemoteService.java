package com.enndfp.shortlink.admin.remote.dto;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.enndfp.shortlink.admin.common.convention.result.Result;
import com.enndfp.shortlink.admin.remote.dto.req.link.LinkPageReqDTO;
import com.enndfp.shortlink.admin.remote.dto.req.link.LinkPageRespDTO;
import com.enndfp.shortlink.admin.remote.dto.resp.link.LinkCreateReqDTO;
import com.enndfp.shortlink.admin.remote.dto.resp.link.LinkCreateRespDTO;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Map;

/**
 * 短链接中台远程调用服务
 *
 * @author Enndfp
 */
public interface LinkRemoteService {

    /**
     * 创建短链接
     *
     * @param linkCreateReqDTO 短链接创建请求传输对象
     * @return 短链接创建响应传输对象
     */
    default Result<LinkCreateRespDTO> create(LinkCreateReqDTO linkCreateReqDTO) {
        // 1. 执行远程调用
        String resultBody = HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/link/create", JSONUtil.toJsonStr(linkCreateReqDTO));

        // 2. 解析响应结果
        return JSON.parseObject(resultBody, new TypeReference<Result<LinkCreateRespDTO>>() {
        });
    }

    /**
     * 分页查询短链接
     *
     * @param linkPageReqDTO 短链接分页请求传输对象
     * @return 短链接分页响应传输对象
     */
    default Result<IPage<LinkPageRespDTO>> pageLink(@ModelAttribute LinkPageReqDTO linkPageReqDTO) {
        // 1. 获取请求参数
        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("gid", linkPageReqDTO.getGid());
        requestParamMap.put("current", linkPageReqDTO.getCurrent());
        requestParamMap.put("size", linkPageReqDTO.getSize());

        // 2. 执行远程调用
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/link/page", requestParamMap);

        // 3. 解析响应结果
        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }


}
