package com.jc.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.entity.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/4 20:24
 * @Description:
 */
@Repository
public interface SysPermissionMapper {
    //获取对应的权限
    SysPermission listSysPermissionByPermissionId(@Param(value="permission_id")Integer permission_id);
    //获取所有权限
    List<SysPermission> list();
    //获取用户的权限信息
    JSONObject loadUserPermission(String username);
    //获取所有菜单
    Set<String> loadAllMenu();
    //获取所有权限
    Set<String> listAllPermission();
}
