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

    @RequestMapping("/listcomment")
    public JSONObject listcomment(HttpServletRequest request){
        return tCommentService.listcomment(LoginUtil.request2Json(request));
    }

    @RequestMapping("/deletecomment")
    public JSONObject deletecomment(@RequestBody JSONObject requestJson){
        LoginUtil.hasAllRequired(requestJson, "postId");
        return tCommentService.deletecomment(requestJson);
    }

}
