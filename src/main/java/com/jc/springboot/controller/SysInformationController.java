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
 * @Description: 系统信息
 */
@RestController
@RequestMapping("/information")
public class SysInformationController {

    @Resource
    SysInformationService informationService;

//    @RequiresPermissions("message:load")
    @RequestMapping("/loadInformation")
    public JSONObject loadInformation(){
        return informationService.loadInformation();
    }

//    @RequiresPermissions("message:load")
    @RequestMapping("/loadMsg")
    public JSONObject loadMsg(@RequestBody JSONObject requestJson){
        return informationService.loadMsg(requestJson);
    }

    @RequiresPermissions("guide:update")
    @RequestMapping("/saveWords")
    public JSONObject saveWords(@RequestBody JSONObject requestJson){
        return informationService.saveWords(requestJson);
    }

    @RequiresPermissions("groupsending:add")
    @RequestMapping("/insertAllInformation")
    public JSONObject insertAllInformation(@RequestBody JSONObject requestJson){
        return informationService.insertAllInformation(requestJson);
    }

    @RequiresPermissions("groupsending:add")
    @RequestMapping("/insertInformation")
    public JSONObject insertInformation(@RequestBody JSONObject requestJson){
        return informationService.insertInformation(requestJson);
    }

//    @RequiresPermissions("message:load")
    @RequestMapping("/loadUserMsg")
    public JSONObject loadUserMsg(@RequestBody JSONObject requestJson){
        return informationService.loadUserMsg(requestJson);
    }

//    @RequiresPermissions("message:load")
    @RequestMapping("/listUserMsg")
    public JSONObject listUserMsg(HttpServletRequest request){
        return informationService.listUserMsg(LoginUtil.request2Json(request));
    }

//    @RequiresPermissions("message:load")
    @RequestMapping("/deleteMsg")
    public JSONObject deleteMsg(@RequestBody JSONObject requestJson){
        return informationService.deleteMsg(requestJson);
    }

}
