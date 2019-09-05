package com.jc.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jc.springboot.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/4 09:56
 * @Description:
 */
@Repository
public interface SysUserMapper{

    SysUser listUserByName(@Param(value="username")String username, @Param("password") String password);

    List<JSONObject> list();

    JSONObject loadUser(@Param("username") String username, @Param("password") String password);

    //查询所有权限, 给角色分配权限
    List<JSONObject> listAllPermission();

    //角色列表
    List<JSONObject> listRole();

    //新增角色
    int insertRole(JSONObject jsonObject);

    //批量插入角色的权限
    int insertRolePermission(@Param("roleId") String roleId, @Param("permissions") List<Integer> permissions);

    //查询某角色的全部数据,在删除和修改角色时调用
    JSONObject getRoleAllInfo(JSONObject jsonObject);

    //修改角色名称
    int updateRoleName(JSONObject jsonObject);

    //将角色曾经拥有而修改为不再拥有的权限 delete_status改为'2'
    int removeOldPermission(@Param("roleId") String roleId, @Param("permissions") List<Integer> permissions);

    //删除角色
    int removeRole(JSONObject jsonObject);

    //删除本角色全部权限
    int removeRoleAllPermission(JSONObject jsonObject);

    //查询用户数量
    int countUser(JSONObject jsonObject);

    //查询用户列表
    List<JSONObject> listUser(JSONObject jsonObject);

    //查询所有的角色
    //在添加/修改用户的时候要使用此方法
    List<JSONObject> listAllRoles();

    //校验用户名是否已存在
    int loadExistUsername(JSONObject jsonObject);

    //新增用户
    int insertUser(JSONObject jsonObject);

    //修改用户
    int updateUser(JSONObject jsonObject);
    int updateuser(JSONObject jsonObject);

}
