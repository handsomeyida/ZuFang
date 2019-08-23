package com.jc.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.service.PostService;
import com.jc.springboot.util.LoginUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
public class PostController {

    @Resource
    PostService postService;

    @RequiresPermissions("basics:list")
    @RequestMapping("/listType")
    public JSONObject listType(HttpServletRequest request){
        return postService.listType(LoginUtil.request2Json(request));
    }
}
