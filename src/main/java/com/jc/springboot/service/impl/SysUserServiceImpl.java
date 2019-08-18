package com.jc.springboot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.dao.SysUserMapper;
import com.jc.springboot.entity.SysUser;
import com.jc.springboot.service.SysUserService;
import com.jc.springboot.util.ErrorEnum;
import com.jc.springboot.util.LoginUtil;
import com.jc.springboot.util.SHA256Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 11:27
 * @Description:
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    SysUserMapper sysUserMapper;

    @Override
    public SysUser selectUserByName(String username, String password) {
        return sysUserMapper.selectUserByName(username, password);
    }

    @Override
    public List<SysUser> list() {
        return sysUserMapper.list();
    }

    @Override
    public JSONObject getUser(String username, String password) {
        return sysUserMapper.getUser(username, password);
    }

    @Override
    public JSONObject listAllPermission() {
        List<JSONObject> permissions = sysUserMapper.listAllPermission();
        return LoginUtil.successPage(permissions);
    }

    @Override
    public JSONObject listRole() {
        List<JSONObject> roles = sysUserMapper.listRole();
        return LoginUtil.successPage(roles);
    }


    //添加角色权限
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject addRole(JSONObject jsonObject) {
        sysUserMapper.insertRole(jsonObject);
        sysUserMapper.insertRolePermission(jsonObject.getString("roleId"), (List<Integer>) jsonObject.get("permissions"));
        return LoginUtil.successJson();
    }

    //修改角色权限
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject updateRole(JSONObject jsonObject) {
        String roleId = jsonObject.getString("roleId");
        List<Integer> newPerms = (List<Integer>) jsonObject.get("permissions");
        JSONObject roleInfo = sysUserMapper.getRoleAllInfo(jsonObject);
        Set<Integer> oldPerms = (Set<Integer>) roleInfo.get("permissionIds");
        //修改角色名称
        dealRoleName(jsonObject, roleInfo);
        //添加新权限
        saveNewPermission(roleId, newPerms, oldPerms);
        //移除旧的不再拥有的权限
        removeOldPermission(roleId, newPerms, oldPerms);
        return LoginUtil.successJson();
    }

    //修改角色权限名称
    private void dealRoleName(JSONObject paramJson, JSONObject roleInfo) {
        String roleName = paramJson.getString("roleName");
        if (!roleName.equals(roleInfo.getString("roleName"))) {
            sysUserMapper.updateRoleName(paramJson);
        }
    }

    //为角色添加新权限
    private void saveNewPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> waitInsert = new ArrayList<>();
        for (Integer newPerm : newPerms) {
            if (!oldPerms.contains(newPerm)) {
                waitInsert.add(newPerm);
            }
        }
        if (waitInsert.size() > 0) {
            sysUserMapper.insertRolePermission(roleId, waitInsert);
        }
    }

    //删除角色,旧的角色不再拥有的权限
    private void removeOldPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> waitRemove = new ArrayList<>();
        for (Integer oldPerm : oldPerms) {
            if (!newPerms.contains(oldPerm)) {
                waitRemove.add(oldPerm);
            }
        }
        if (waitRemove.size() > 0) {
            sysUserMapper.removeOldPermission(roleId, waitRemove);
        }
    }

    //删除角色权限
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject deleteRole(JSONObject jsonObject) {
        JSONObject roleInfo = sysUserMapper.getRoleAllInfo(jsonObject);
        List<JSONObject> users = (List<JSONObject>) roleInfo.get("users");
        if (users != null && users.size() > 0) {
            return LoginUtil.errorJson(ErrorEnum.E_10008);
        }
        sysUserMapper.removeRole(jsonObject);
        sysUserMapper.removeRoleAllPermission(jsonObject);
        return LoginUtil.successJson();
    }

    /**
     * 用户列表
     */
    @Override
    public JSONObject listUser(JSONObject jsonObject) {
        LoginUtil.fillPageParam(jsonObject);
        int count = sysUserMapper.countUser(jsonObject);
        List<JSONObject> list = sysUserMapper.listUser(jsonObject);
        return LoginUtil.successPage(jsonObject, list, count);
    }

    /**
     * 添加用户
     */
    @Override
    public JSONObject addUser(JSONObject jsonObject) {
        int exist = sysUserMapper.queryExistUsername(jsonObject);
        if (exist > 0) {
            return LoginUtil.errorJson(ErrorEnum.E_10009);
        }
        sysUserMapper.addUser(jsonObject);
        return LoginUtil.successJson();
    }

    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     */
    @Override
    public JSONObject getAllRoles() {
        List<JSONObject> roles = sysUserMapper.getAllRoles();
        return LoginUtil.successPage(roles);
    }

    /**
     * 修改用户
     */
    @Override
    public JSONObject updateUser(JSONObject jsonObject) {
        String loginword = (String)jsonObject.get("password");
        String password = SHA256Util.sha256(loginword, null);
        jsonObject.remove("password");
        jsonObject.put("password",password);
        sysUserMapper.updateUser(jsonObject);
        return LoginUtil.successJson();
    }

}
