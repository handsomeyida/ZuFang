package com.jc.springboot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 11:22
 * @Description: 权限实体类
 */
@Data
public class SysPermission implements Serializable {

    private int id;

    private String menu_code;

    private String menu_name;

    private String permission_code;

    private String permission_name;

    private Integer required_permission;

    public SysPermission() {
    }
}
