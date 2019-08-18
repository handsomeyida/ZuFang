package com.jc.springboot.service;

import com.jc.springboot.entity.SysRole;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 11:27
 * @Description:
 */
public interface SysRoleService {

    List<SysRole> selectSysRoleByRoleId(Integer role_id);

    List<SysRole> list();

}
