package com.jc.springboot.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/15 14:52
 * @Description:
 */
public interface LoginService {
    //获取用户登录信息
    JSONObject listInfo();
    //登出
    JSONObject logout();
}
