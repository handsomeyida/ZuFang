package com.jc.springboot.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.entity.SysPermission;
import com.jc.springboot.entity.SysRole;
import com.jc.springboot.entity.SysRolePermission;
import com.jc.springboot.entity.SysUser;
import com.jc.springboot.service.SysPermissionService;
import com.jc.springboot.service.SysRolePermissionService;
import com.jc.springboot.service.SysRoleService;
import com.jc.springboot.service.SysUserService;
import com.jc.springboot.util.Constants;
import com.jc.springboot.util.SHA256Util;
import com.jc.springboot.util.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 11:08
 * @Description: Shiro权限匹配和账号密码匹配,用于授权和认证
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String) authenticationToken.getPrincipal();
        //通过username,password从数据库中查找 User对象，如果找到进行验证
        String loginword = new String((char[]) authenticationToken.getCredentials());
        String password = SHA256Util.sha256(loginword, null);
        //实际项目中,这里可以根据实际情况做缓存,如果不做,Shiro自己也是有时间间隔机制,2分钟内不会重复执行该方法
        JSONObject user = sysUserService.loadUser(username,password);
        SysUser sysUser = sysUserService.listUserByName(username, password);
        //判断账号是否存在
        if (sysUser == null) {
            throw new AuthenticationException();
        }
        //因为存数据库的是没有加密过的密码,所以不需要比对
        //判断账号是否被冻结
        if (sysUser.getDelete_status()==null||sysUser.getDelete_status().equals(2)){
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(sysUser, password, getName());
        user.remove("password");
        //进行验证
        //验证成功
        SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_INFO, user);
        ShiroUtils.deleteCache(username,true);
        return authenticationInfo;
    }

    /**
     * 授权
     * 用户进行权限验证时候Shiro会去缓存中找,如果查不到数据,会执行这个方法去查权限,并放入缓存中
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser sysUser = (SysUser) principalCollection.getPrimaryPrincipal();
        //获取用户ID
        Integer role_id =sysUser.getRole_id();
        //这里可以进行授权和处理
        Set<String> rolesSet = new HashSet<>();
        Set<String> permsSet = new HashSet<>();
        //查询角色和权限(这里根据业务自行查询)
        List<SysRolePermission> sysRolePermissionList = sysRolePermissionService.listSysPermissionIDByRoleId(role_id);
        for (SysRolePermission sysRolePermission:sysRolePermissionList) {
            Integer permission_id = sysRolePermission.getPermission_id();
            SysPermission sysPermission = sysPermissionService.listSysPermissionByPermissionId(permission_id);
            permsSet.add(sysPermission.getPermission_code());
            List<SysRole> sysRoleList = sysRoleService.listSysRoleByRoleId(sysRolePermission.getRole_id());
            for (SysRole sysRole :sysRoleList) {
                rolesSet.add(sysRole.getRole_name());
            }
        }
        //将查到的权限和角色分别传入authorizationInfo中
        authorizationInfo.setStringPermissions(permsSet);
        authorizationInfo.setRoles(rolesSet);
        return authorizationInfo;
    }


}
