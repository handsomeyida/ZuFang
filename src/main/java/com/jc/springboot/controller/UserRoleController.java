package com.jc.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.service.SysUserService;
import com.jc.springboot.util.LoginUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 14:49
 * @Description: 后台用户/菜单/权限相关
 */

@RestController
@RequestMapping("/role")
public class UserRoleController {

    @Resource
    SysUserService userService;

    //查询所有权限, 给角色分配权限时调用
    @RequiresPermissions("role:list")
    @RequestMapping("/AllPermissionlist")
    public JSONObject listAllPermission() {
        return userService.listAllPermission();
    }

    //获取角色信息
    @RequiresPermissions("role:list")
    @RequestMapping("/listRole")
    public JSONObject listRole() {
        return userService.listRole();
    }

    //添加角色权限
    @RequiresPermissions("role:add")
    @RequestMapping("/addRole")
    public JSONObject insertRole(@RequestBody JSONObject requestJson) {
        LoginUtil.hasAllRequired(requestJson, "roleName,permissions");
        return userService.insertRole(requestJson);
    }

    //修改角色权限
    @RequiresPermissions("role:update")
    @PostMapping("/updateRole")
    public JSONObject updateRole(@RequestBody JSONObject requestJson) {
        LoginUtil.hasAllRequired(requestJson, "roleId,roleName,permissions");
        return userService.updateRole(requestJson);
    }

    //删除角色权限
    @RequiresPermissions("role:delete")
    @PostMapping("/deleteRole")
    public JSONObject deleteRole(@RequestBody JSONObject requestJson) {
        LoginUtil.hasAllRequired(requestJson, "roleId");
        return userService.deleteRole(requestJson);
    }

}
