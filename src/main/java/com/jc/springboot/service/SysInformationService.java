package com.jc.springboot.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/21 09:29
 * @Description: 信息管理
 */
public interface SysInformationService {

    //获取公告信息
    JSONObject loadInformation();
    //获取用户收到的消息(弹框)
    JSONObject loadMsg(JSONObject jsonObject);
    //获取指定用户的信息详情
    JSONObject loadUserMsg(JSONObject jsonObject);
    //获取指定用户的信息详情(有分页)
    JSONObject listUserMsg(JSONObject jsonObject);
    //修改公告显示信息
    JSONObject saveWords(JSONObject jsonObject);
    //群发消息
    JSONObject insertAllInformation(JSONObject jsonObject);
    //像指定用户发送信息
    JSONObject insertInformation(JSONObject jsonObject);
    //把消息设为已读
    JSONObject deleteMsg(JSONObject jsonObject);
}
