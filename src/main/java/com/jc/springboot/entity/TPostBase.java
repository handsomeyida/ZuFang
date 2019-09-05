package com.jc.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/30 12:57
 * @Description:
 */
@Data
public class TPostBase implements Serializable {

    private int id;
    private String user_id;
    private String content;
    private String min_price;
    private String max_price;
    private String phone;
    private String address;
    private String labels;
    private int total_browse;
    private int total_resonate;
    private int dev_browse;
    private int dev_resonate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date active_time;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    private int is_lower_shelf;
    private int is_del;
    private int total_collection;
    private int total_comments;
    private int fee;
    private String title;
    private String weeks;
    private String count;
    private String money;
    private String around;

    public TPostBase() {
    }
}
