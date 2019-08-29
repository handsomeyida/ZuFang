package com.jc.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.service.TPostService;
import com.jc.springboot.util.LoginUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/23 14:32
 * @Description:
 */
@RestController
@RequestMapping("/basics")
public class TPostController {

    @Resource
    TPostService postService;

    @RequiresPermissions("basics:list")
    @RequestMapping("/listType")
    public JSONObject listType(HttpServletRequest request){
        return postService.listType(LoginUtil.request2Json(request));
    }

    @RequiresPermissions("basics:list")
    @RequestMapping("/listhomelabels")
    public JSONObject listhomelabels(HttpServletRequest request){
        return postService.listhomelabels(LoginUtil.request2Json(request));
    }

    @RequiresPermissions("basics:add")
    @RequestMapping("/inserthomelabels")
    public JSONObject inserthomelabels(@RequestBody JSONObject requestJson){
        return postService.inserthomelabels(requestJson);
    }

    @RequiresPermissions("basics:update")
    @RequestMapping("/updatehomelabels")
    public JSONObject updatehomelabels(@RequestBody JSONObject requestJson){
        return postService.updatehomelabels(requestJson);
    }

    @RequiresPermissions("basics:delete")
    @RequestMapping("/deletehomelabels")
    public JSONObject deletehomelabels(@RequestBody JSONObject requestJson){
        return postService.deletehomelabels(requestJson);
    }

    @RequiresPermissions("basics:list")
    @RequestMapping("/listshoplabels")
    public JSONObject listshoplabels(){
        return postService.listshoplabels();
    }

    @RequiresPermissions("basics:add")
    @RequestMapping("/insertshoplabels")
    public JSONObject insertshoplabels(@RequestBody JSONObject requestJson){
        return postService.insertshoplabels(requestJson);
    }

    @RequiresPermissions("basics:update")
    @RequestMapping("/updateshoplabels")
    public JSONObject updateshoplabels(@RequestBody JSONObject requestJson){
        return postService.updateshoplabels(requestJson);
    }

    @RequiresPermissions("basics:delete")
    @RequestMapping("/deleteonelabels")
    public JSONObject deleteonelabels(@RequestBody JSONObject requestJson){
        return postService.deleteonelabels(requestJson);
    }
    @RequiresPermissions("basics:delete")
    @RequestMapping("/deletealllabels")
    public JSONObject deletealllabels(@RequestBody JSONObject requestJson){
        return postService.deletealllabels(requestJson);
    }

    @RequiresPermissions("basics:list")
    @RequestMapping("/listsubwaylabels")
    public JSONObject listsubwaylabels(){
        return postService.listsubwaylabels();
    }

    @RequiresPermissions("basics:add")
    @RequestMapping("/insertsubwaylabels")
    public JSONObject insertsubwaylabels(@RequestBody JSONObject requestJson){
        return postService.insertsubwaylabels(requestJson);
    }

}
