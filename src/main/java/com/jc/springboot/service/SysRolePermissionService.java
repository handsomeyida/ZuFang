package com.jc.springboot.service;

import com.jc.springboot.entity.SysRolePermission;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 11:29
 * @Description:
 */
public interface SysRolePermissionService {
    //获取角色
    List<SysRolePermission> listSysPermissionIDByRoleId(Integer role_id);
    //添加角色权限
    boolean save(SysRolePermission sysRolePermission);
}
