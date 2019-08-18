package com.jc.springboot.service.impl;

import com.jc.springboot.dao.SysRolePermissionMapper;
import com.jc.springboot.entity.SysRolePermission;
import com.jc.springboot.service.SysRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 11:29
 * @Description:
 */
@Slf4j
@Service
public class SysRolePermissionServiceImpl implements SysRolePermissionService {

    @Resource
    SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<SysRolePermission> selectSysPermissionIDByRoleId(Integer role_id) {
        return sysRolePermissionMapper.selectSysPermissionIDByRoleId(role_id);
    }

    @Override
    public boolean save(SysRolePermission sysRolePermission) {
        return sysRolePermissionMapper.save(sysRolePermission);
    }
}
