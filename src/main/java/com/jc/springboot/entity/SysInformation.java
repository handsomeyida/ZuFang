package com.jc.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/20 14:29
 * @Description:
 */
@Data
public class SysInformation implements Serializable {
    private int id;

    private String target_id;

    private String content;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    public SysInformation() {
    }

    public SysInformation(String target_id, String content, Date create_time) {
        this.target_id = target_id;
        this.content = content;
        this.create_time = create_time;
    }
}
