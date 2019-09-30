package com.jc.springboot.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 11:18
 * @Description: 角色实体类
 */
@Data
public class SysRole implements Serializable {

    private int id;

    private String role_name;

    private Timestamp create_time;

    private Timestamp update_time;

    private String delete_status;

    public SysRole() {
    }
}
