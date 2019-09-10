package com.jc.springboot.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/9/6 10:45
 * @Description:
 */
public interface TCommentService {
    //获取指定贴子的所有评论
    JSONObject listcomment(JSONObject jsonObject);
    //获取用户收藏的贴子
    JSONObject listcollectionbase(JSONObject jsonObject);
    //删除指定评论
    JSONObject deletecomment(JSONObject jsonObject);
    //取消收藏
    JSONObject deletecollectionbase(JSONObject jsonObject);
    //对用户禁言
    JSONObject insertlimit(JSONObject jsonObject);
    //判断禁言是否到期
    JSONObject loadendtime(JSONObject jsonObject);
}
