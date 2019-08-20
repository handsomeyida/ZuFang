package com.jc.springboot.service;

import com.jc.springboot.entity.SysRolePermission;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 11:29
 * @Description:
 */
public interface SysRolePermissionService {

    List<SysRolePermission> listSysPermissionIDByRoleId(Integer role_id);

    boolean save(SysRolePermission sysRolePermission);
}
