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


    @Override
    public JSONObject loadInformation() {
        JSONObject info = new JSONObject();
        JSONObject loadInformation = informationMapper.loadInformation();
        info.put("loadInformation", loadInformation);
        return LoginUtil.successJson(info);
    }

    @Override
    public JSONObject loadMsg(JSONObject jsonObject) {
        LoginUtil.hasAllRequired(jsonObject, "userId");
        JSONObject info = new JSONObject();
        List<JSONObject> loadMsg = informationMapper.loadMsg(jsonObject);
        info.put("loadMsg", loadMsg);
        return LoginUtil.successJson(info);
    }

    @Override
    public JSONObject loadUserMsg(JSONObject jsonObject) {
        LoginUtil.hasAllRequired(jsonObject, "userId");
        JSONObject info = new JSONObject();
        List<JSONObject> loadUserMsg = informationMapper.loadUserMsg(jsonObject);
        info.put("list", loadUserMsg);
        return LoginUtil.successJson(info);
    }

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

    @Override
    public JSONObject saveWords(JSONObject jsonObject) {
        String content = jsonObject.getString("content");
        informationMapper.saveWords(content);
        return LoginUtil.successJson();
    }

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

    @Override
    public JSONObject deleteMsg(JSONObject jsonObject) {
        informationMapper.deleteMsg(jsonObject);
        return LoginUtil.successJson();
    }
}
