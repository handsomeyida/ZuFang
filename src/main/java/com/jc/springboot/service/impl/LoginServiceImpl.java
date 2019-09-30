package com.jc.springboot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.service.LoginService;
import com.jc.springboot.service.SysPermissionService;
import com.jc.springboot.util.Constants;
import com.jc.springboot.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/15 14:52
 * @Description:
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    SysPermissionService permissionService;
    //获取用户登录信息
    @Override
    public JSONObject listInfo() {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
        String username = userInfo.getString("username");
        JSONObject info = new JSONObject();
        JSONObject userPermission = permissionService.loadUserPermission(username);
        session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
        info.put("userPermission", userPermission);
        return LoginUtil.successJson(info);
    }
    //登出
    @Override
    public JSONObject logout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {
        }
        return LoginUtil.successJson();
    }
}
