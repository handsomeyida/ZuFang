package com.jc.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.entity.SysUser;
import com.jc.springboot.service.LoginService;
import com.jc.springboot.service.SysUserService;
import com.jc.springboot.util.ErrorEnum;
import com.jc.springboot.util.LoginUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 15:03
 * @Description: 用户登录
 */
@RestController
@RequestMapping("/Login")
public class SysUserLoginController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    LoginService loginService;

    /**
     * 登录
     * @Author 帅气的达
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject login(@RequestBody SysUser sysUser){
        //进行身份验证
        JSONObject info = new JSONObject();
        try{
            //验证身份和登陆
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUsername(), sysUser.getPassword());
            try {
                //验证成功进行登录操作
                subject.login(token);
                info.put("result", "success");
            } catch (AuthenticationException e) {
                info.put("result", "fail");
            }
        }catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return LoginUtil.errorJson(ErrorEnum.E_503);
        } catch (LockedAccountException e) {
            return LoginUtil.errorJson(ErrorEnum.E_504);
        } catch (AuthenticationException e) {
            return LoginUtil.errorJson(ErrorEnum.E_505);
        } catch (Exception e) {
            return LoginUtil.errorJson(ErrorEnum.E_506);
        }
        return LoginUtil.successJson(info);
    }

    /**
     * 未登录
     * @Author 帅气的达
     */
    @RequestMapping("/unauth")
    public JSONObject unauth(){

        return LoginUtil.errorJson(ErrorEnum.E_20011);
    }

    /**
     * 获取用户登录信息
     */
    @RequestMapping("/getinfo")
    public JSONObject listInfo() {
        return loginService.listInfo();
    }

    @RequestMapping("/logout")
    public JSONObject logout() {
        return loginService.logout();
    }
}
