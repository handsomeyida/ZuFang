package com.jc.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.service.SysInformationService;
import com.jc.springboot.util.LoginUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/20 14:11
 * @Description: 信息管理
 */
@RestController
@RequestMapping("/information")
public class SysInformationController {

    @Resource
    SysInformationService informationService;

    //获取公告信息
    @RequestMapping("/loadInformation")
    public JSONObject loadInformation(){
        return informationService.loadInformation();
    }

    //获取用户收到的消息(弹框)
    @RequestMapping("/loadMsg")
    public JSONObject loadMsg(@RequestBody JSONObject requestJson){
        return informationService.loadMsg(requestJson);
    }
    //修改公告显示信息
    @RequiresPermissions("guide:update")
    @RequestMapping("/saveWords")
    public JSONObject saveWords(@RequestBody JSONObject requestJson){
        return informationService.saveWords(requestJson);
    }
    //群发消息
    @RequiresPermissions("groupsending:add")
    @RequestMapping("/insertAllInformation")
    public JSONObject insertAllInformation(@RequestBody JSONObject requestJson){
        return informationService.insertAllInformation(requestJson);
    }
    //像指定用户发送信息
    @RequiresPermissions("groupsending:add")
    @RequestMapping("/insertInformation")
    public JSONObject insertInformation(@RequestBody JSONObject requestJson){
        return informationService.insertInformation(requestJson);
    }
    //获取指定用户的信息详情
//    @RequiresPermissions("message:load")
    @RequestMapping("/loadUserMsg")
    public JSONObject loadUserMsg(@RequestBody JSONObject requestJson){
        return informationService.loadUserMsg(requestJson);
    }
    //获取指定用户的信息详情(有分页)
//    @RequiresPermissions("message:load")
    @RequestMapping("/listUserMsg")
    public JSONObject listUserMsg(HttpServletRequest request){
        return informationService.listUserMsg(LoginUtil.request2Json(request));
    }
    //把消息设为已读
//    @RequiresPermissions("message:load")
    @RequestMapping("/deleteMsg")
    public JSONObject deleteMsg(@RequestBody JSONObject requestJson){
        return informationService.deleteMsg(requestJson);
    }

}
