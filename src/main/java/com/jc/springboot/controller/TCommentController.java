package com.jc.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.service.TCommentService;
import com.jc.springboot.util.LoginUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: 帅气的达
 * @Date: 2019/9/6 14:07
 * @Description: 评论
 */
@RestController
@RequestMapping("/comment")
public class TCommentController {

    @Resource
    TCommentService tCommentService;
    //获取指定贴子的所有评论
    @RequestMapping("/listcomment")
    public JSONObject listcomment(HttpServletRequest request){
        return tCommentService.listcomment(LoginUtil.request2Json(request));
    }
    //获取用户收藏的贴子
    @RequestMapping("/listcollectionbase")
    public JSONObject listcollectionbase(HttpServletRequest request){
        return tCommentService.listcollectionbase(LoginUtil.request2Json(request));
    }
    //删除指定评论
    @RequestMapping("/deletecomment")
    public JSONObject deletecomment(@RequestBody JSONObject requestJson){
        LoginUtil.hasAllRequired(requestJson, "ID");
        return tCommentService.deletecomment(requestJson);
    }
    //取消收藏
    @RequestMapping("/deletecollectionbase")
    public JSONObject deletecollectionbase(@RequestBody JSONObject requestJson){
        LoginUtil.hasAllRequired(requestJson, "ID");
        return tCommentService.deletecollectionbase(requestJson);
    }
    //对用户禁言
    @RequestMapping("/insertlimit")
    public JSONObject insertlimit(@RequestBody JSONObject requestJson){
        return tCommentService.insertlimit(requestJson);
    }
    //判断用户是否禁言
    @RequestMapping("/loadendtime")
    public JSONObject loadendtime(@RequestBody JSONObject requestJson){
        return tCommentService.loadendtime(requestJson);
    }
}
