package com.jc.springboot.dao;

import com.jc.springboot.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/4 20:23
 * @Description:
 */
@Repository
public interface SysRoleMapper {
    //获取对应的角色
    List<SysRole> listSysRoleByRoleId(@Param(value="role_id")Integer role_id);
    //获取所有角色
    List<SysRole> list();
}
