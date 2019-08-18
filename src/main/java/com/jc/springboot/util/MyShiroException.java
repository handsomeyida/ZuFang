package com.jc.springboot.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 10:47
 * @Description: 自定义异常
 */
@ControllerAdvice
public class MyShiroException {

    /**
     * 处理Shiro权限拦截异常
     * 如果返回JSON数据格式请加上 @ResponseBody注解
     * @Author Sans
     * @CreateTime 2019/6/15 13:35
     * @Return Map<Object> 返回结果集
     */

    @ResponseBody
    @ExceptionHandler(value = AuthorizationException.class)
    public JSONObject defaultErrorHandler(){
        JSONObject info = new JSONObject();
        return LoginUtil.errorJson(ErrorEnum.E_20012);
    }
//    @ResponseBody
//    @ExceptionHandler(value = AuthorizationException.class)
//    public Map<String,Object> defaultErrorHandler(){
//        Map<String,Object> map = new HashMap<>();
//        map.put("403","警告:权限不足!!请联系益达~~");
//        return map;
//    }

}
