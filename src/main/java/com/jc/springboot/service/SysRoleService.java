package com.jc.springboot.service;

import com.jc.springboot.entity.SysRole;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 11:27
 * @Description:
 */
public interface SysRoleService {
    //获取角色
    List<SysRole> listSysRoleByRoleId(Integer role_id);
    //获取所有角色
    List<SysRole> list();

}
