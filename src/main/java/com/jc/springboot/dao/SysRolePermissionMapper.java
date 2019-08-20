package com.jc.springboot.dao;

import com.jc.springboot.entity.SysRolePermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/4 20:24
 * @Description:
 */
@Repository
public interface SysRolePermissionMapper {

    List<SysRolePermission> loadSysPermissionIDByRoleId(@Param(value="role_id")Integer role_id);

    boolean save(SysRolePermission sysRolePermission);

}
