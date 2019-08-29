package com.jc.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.service.TNavigationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/28 14:18
 * @Description:
 */
@RestController
@RequestMapping("/navigation")
public class TNavigationController {

    @Resource
    TNavigationService tNavigationService;

    @RequiresPermissions("navigation:list")
    @RequestMapping("/listnavigation")
    public JSONObject listnavigation(){
        return tNavigationService.listnavigation();
    }
}
