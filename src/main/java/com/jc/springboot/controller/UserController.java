package com.jc.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.service.SysUserService;
import com.jc.springboot.util.LoginUtil;
import com.jc.springboot.util.SHA256Util;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/16 14:40
 * @Description:
 */
@RestController
@RequestMapping("/backstageuser")
public class UserController {

    @Resource
    SysUserService userService;

    //查询用户列表
    @RequiresPermissions("user:list")
    @RequestMapping("/list")
    public JSONObject listUser(HttpServletRequest request) {
        return userService.listUser(LoginUtil.request2Json(request));
    }

    @RequiresPermissions("user:add")
    @RequestMapping("/addUser")
    public JSONObject addUser(@RequestBody JSONObject requestJson) {
        String loginword = (String)requestJson.get("password");
        String password = SHA256Util.sha256(loginword, null);
        requestJson.remove("password");
        requestJson.put("password",password);
        LoginUtil.hasAllRequired(requestJson, "username, password, nickname, roleId");
        return userService.addUser(requestJson);
    }

    @RequiresPermissions("user:update")
    @RequestMapping("/updateUser")
    public JSONObject updateUser(@RequestBody JSONObject requestJson) {
        LoginUtil.hasAllRequired(requestJson, " nickname, roleId, deleteStatus, userId");
        return userService.updateUser(requestJson);
    }

    @RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
    @RequestMapping("/getAllRoles")
    public JSONObject getAllRoles() {
        return userService.getAllRoles();
    }


}
