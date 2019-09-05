package com.jc.springboot.service;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 11:26
 * @Description:
 */
public interface SysUserService {

    SysUser listUserByName(String username, String password);

    JSONObject list();

    //登陆获取用户信息
    JSONObject loadUser(@Param("username") String username, @Param("password") String password);

    //查询所有权限, 给角色分配权限时调用
    JSONObject listAllPermission();

    //角色权限列表
    JSONObject listRole();

    //添加角色权限
    JSONObject insertRole(JSONObject jsonObject);

    // 修改角色权限
    JSONObject updateRole(JSONObject jsonObject);

    //删除角色权限
    JSONObject deleteRole(JSONObject jsonObject);

    //用户列表
    JSONObject listUser(JSONObject jsonObject);

     //查询所有的角色
     //在添加/修改用户的时候要使用此方法
    JSONObject getAllRoles();

    //添加用户
    JSONObject insertUser(JSONObject jsonObject);

    //修改用户
    JSONObject updateUser(JSONObject jsonObject);
    JSONObject updateuser(JSONObject jsonObject);


}
