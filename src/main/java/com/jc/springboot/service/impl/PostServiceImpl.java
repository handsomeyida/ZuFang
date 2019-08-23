package com.jc.springboot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.dao.PostMapper;
import com.jc.springboot.service.PostService;
import com.jc.springboot.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/23 14:34
 * @Description:
 */
@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Resource
    PostMapper postMapper;

    @Override
    public JSONObject listType(JSONObject jsonObject) {
        LoginUtil.fillPageParam(jsonObject);
        JSONObject info = new JSONObject();
        List<JSONObject> listType = postMapper.listType(jsonObject);
        int count = postMapper.countType(jsonObject);
        info.put("list", listType);
        return LoginUtil.successPage(jsonObject, listType, count);
    }

}
