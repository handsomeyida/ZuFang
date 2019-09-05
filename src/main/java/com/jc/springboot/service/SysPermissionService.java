package com.jc.springboot.service;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.entity.SysPermission;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 11:29
 * @Description:
 */
public interface SysPermissionService {
    //根据用户id获取用户所有权限
    SysPermission listSysPermissionByPermissionId(Integer permission_id);
    //获取所有权限
    List<SysPermission> list();
    //获取用户的所有权限
    JSONObject loadUserPermission(String username);
}
