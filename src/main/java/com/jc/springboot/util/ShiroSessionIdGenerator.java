package com.jc.springboot.util;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 11:05
 * @Description: 自定义SessionId生成器
 */

public class ShiroSessionIdGenerator implements SessionIdGenerator {

    /**
     * 实现SessionId生成
     * @Author 帅气的达
     * @CreateTime 2019/8/3 11:05
     */
    @Override
    public Serializable generateId(Session session) {

        Serializable sessionId = new JavaUuidSessionIdGenerator().generateId(session);
        return String.format("login_token_%s", sessionId);
    }
}
