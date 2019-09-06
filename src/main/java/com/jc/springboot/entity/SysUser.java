package com.jc.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 11:12
 * @Description: 用户实体类
 */
@Data
public class SysUser implements Serializable {

    private int id;

    @NotNull(message="用户名不允许为空")
    private String username;

    @NotNull(message="密码不允许为空")
    private String password;

    private String nickname;

    private int role_id;

//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp create_time;

//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp update_time;

    private String delete_status;

    public SysUser() {
    }
}
