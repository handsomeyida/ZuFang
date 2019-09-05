package com.jc.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/30 10:29
 * @Description:
 */
@Data
public class TWXUser implements Serializable {

    private String user_id;
    private String open_id;
    private String union_id;
    private String wx_avatar_url;
    private String wx_nick_name;
    private String wx_gender;
    private String wx_city;
    private String wx_province;
    private String wx_country;
    private int dev_fans;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;
    private int is_del;

    public TWXUser() {
    }
}
