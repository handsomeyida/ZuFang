package com.jc.springboot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.dao.SysPermissionMapper;
import com.jc.springboot.entity.SysPermission;
import com.jc.springboot.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 11:29
 * @Description:
 */
@Slf4j
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Resource
    SysPermissionMapper sysPermissionMapper;
    //根据用户id获取用户所有权限
    @Override
    public SysPermission listSysPermissionByPermissionId(Integer permission_id) {
        return sysPermissionMapper.listSysPermissionByPermissionId(permission_id);
    }
    //获取所有权限
    @Override
    public List<SysPermission> list() {
        return sysPermissionMapper.list();
    }
    //获取用户的所有权限
    @Override
    public JSONObject loadUserPermission(String username) {
        JSONObject userPermission = sysPermissionMapper.loadUserPermission(username);
        //管理员角色ID为1
        int adminRoleId = 1;
        //如果是管理员
        String roleIdKey = "roleId";
        if (adminRoleId == userPermission.getIntValue(roleIdKey)) {
            //查询所有菜单  所有权限
            Set<String> menuList = sysPermissionMapper.loadAllMenu();
            Set<String> permissionList = sysPermissionMapper.listAllPermission();
            userPermission.put("menuList", menuList);
            userPermission.put("permissionList", permissionList);
        }
        return userPermission;
    }
}
