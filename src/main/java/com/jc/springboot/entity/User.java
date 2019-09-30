package com.jc.springboot.entity;

import java.io.Serializable;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 09:52
 * @Description: 权限测试用户实体类
 */

public class User implements Serializable {
    private int id;
    private String user_name;
    private String user_password;

    public User() {
    }

    public User(String user_name, String user_password) {
        this.user_name = user_name;
        this.user_password = user_password;
    }

    public User(int id, String user_name, String user_password) {
        this.id = id;
        this.user_name = user_name;
        this.user_password = user_password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
