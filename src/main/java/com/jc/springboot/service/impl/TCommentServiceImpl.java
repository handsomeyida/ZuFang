package com.jc.springboot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.dao.TCommentMapper;
import com.jc.springboot.service.TCommentService;
import com.jc.springboot.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/9/6 10:46
 * @Description:
 */
@Slf4j
@Service
public class TCommentServiceImpl implements TCommentService {

    @Resource
    TCommentMapper tCommentMapper;
    //获取指定贴子的所有评论
    @Override
    public JSONObject listcomment(JSONObject jsonObject) {
        LoginUtil.fillPageParam(jsonObject);
        JSONObject info = new JSONObject();
        int count = tCommentMapper.countcomment(jsonObject);
        List<JSONObject> list = tCommentMapper.listcomment(jsonObject);
        info.put("list", list);
        return LoginUtil.successPage(jsonObject, list, count);
    }
    //删除指定评论
    @Override
    public JSONObject deletecomment(JSONObject jsonObject) {
        tCommentMapper.deletecomment(jsonObject);
        return LoginUtil.successJson();
    }
}
