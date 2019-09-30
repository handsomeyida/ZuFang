package com.jc.springboot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.dao.SysInformationMapper;
import com.jc.springboot.service.SysInformationService;
import com.jc.springboot.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/21 09:29
 * @Description:
 */
@Slf4j
@Service
public class SysInformationServiceImpl implements SysInformationService {

    @Resource
    SysInformationMapper informationMapper;

    //获取公告信息
    @Override
    public JSONObject loadInformation() {
        JSONObject info = new JSONObject();
        JSONObject loadInformation = informationMapper.loadInformation();
        info.put("loadInformation", loadInformation);
        return LoginUtil.successJson(info);
    }
    //获取用户收到的消息(弹框)
    @Override
    public JSONObject loadMsg(JSONObject jsonObject) {
        LoginUtil.hasAllRequired(jsonObject, "userId");
        JSONObject info = new JSONObject();
        List<JSONObject> loadMsg = informationMapper.loadMsg(jsonObject);
        info.put("loadMsg", loadMsg);
        return LoginUtil.successJson(info);
    }
    //获取指定用户的信息详情
    @Override
    public JSONObject loadUserMsg(JSONObject jsonObject) {
        LoginUtil.hasAllRequired(jsonObject, "userId");
        JSONObject info = new JSONObject();
        List<JSONObject> loadUserMsg = informationMapper.loadUserMsg(jsonObject);
        info.put("list", loadUserMsg);
        return LoginUtil.successJson(info);
    }
    //获取指定用户的信息详情(有分页)
    @Override
    public JSONObject listUserMsg(JSONObject jsonObject) {
        LoginUtil.hasAllRequired(jsonObject, "userId");
        LoginUtil.fillPageParam(jsonObject);
        JSONObject info = new JSONObject();
        List<JSONObject> loadUserMsg = informationMapper.listUserMsg(jsonObject);
        int count = informationMapper.countUserMsg(jsonObject);
        info.put("list", loadUserMsg);
        return LoginUtil.successPage(jsonObject, loadUserMsg, count);
    }
    //修改公告显示信息
    @Override
    public JSONObject saveWords(JSONObject jsonObject) {
        String content = jsonObject.getString("content");
        informationMapper.saveWords(content);
        return LoginUtil.successJson();
    }
    //群发消息
    @Override
    public JSONObject insertAllInformation(JSONObject jsonObject) {
        Date createTime2 = new Date();
        jsonObject.put("createTime", createTime2);
        List<Integer> userIds = (List<Integer>) jsonObject.get("userIds");
        String userId = jsonObject.getString("userId");
        String content = jsonObject.getString("content");
        Date createTime = (Date) jsonObject.get("createTime");
        informationMapper.insertAllInformation(jsonObject);
        informationMapper.insertUserInformation(userId,userIds,content,createTime);
        return LoginUtil.successJson();
    }
    //像指定用户发送信息
    @Override
    public JSONObject insertInformation(JSONObject jsonObject) {
        Date createTime2 = new Date();
        jsonObject.put("createTime", createTime2);
        String userIds = jsonObject.getString("userIds");
        String userId = jsonObject.getString("userId");
        String content = jsonObject.getString("content");
        Date createTime = (Date) jsonObject.get("createTime");
        informationMapper.insertOneUserInformation(userId,userIds,content,createTime);
        return LoginUtil.successJson();
    }
    //把消息设为已读
    @Override
    public JSONObject deleteMsg(JSONObject jsonObject) {
        informationMapper.deleteMsg(jsonObject);
        return LoginUtil.successJson();
    }
    //获取所有系统通知消息
    @Override
    public JSONObject listsysinfomation(JSONObject jsonObject) {
        LoginUtil.fillPageParam(jsonObject);
        JSONObject info = new JSONObject();
        List<JSONObject> list = informationMapper.listsysinfomation(jsonObject);
        int count = informationMapper.countsysinfomation(jsonObject);
        info.put("list", list);
        return LoginUtil.successPage(jsonObject, list, count);
    }
}
