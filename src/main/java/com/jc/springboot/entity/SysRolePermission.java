package com.jc.springboot.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 13:52
 * @Description:
 */
@Data
public class SysRolePermission implements Serializable {

    private int id;
    
    private int role_id;
    
    private int permission_id;
    
    private Timestamp create_time;

    private Timestamp update_time;
    
    private String delete_status;

    public SysRolePermission() {
    }
}
