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

    SysPermission listSysPermissionByPermissionId(Integer permission_id);

    List<SysPermission> list();

    JSONObject loadUserPermission(String username);
}
