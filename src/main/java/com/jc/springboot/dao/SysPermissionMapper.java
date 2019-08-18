package com.jc.springboot.dao;

import com.alibaba.fastjson.JSONObject;
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

    com.jc.springboot.entity.SysPermission selectSysPermissionByPermissionId(@Param(value="permission_id")Integer permission_id);

    List<com.jc.springboot.entity.SysPermission> list();

    JSONObject getUserPermission(String username);

    Set<String> getAllMenu();

    Set<String> getAllPermission();
}
