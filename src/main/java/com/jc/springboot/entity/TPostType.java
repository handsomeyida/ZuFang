package com.jc.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/23 16:12
 * @Description: 帖子类型实体类
 */
@Data
public class TPostType implements Serializable {

    private int id;

    private String type_imtg_url;

    private String type_name;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    private int is_del;

    public TPostType() {
    }

    public TPostType(int id, String type_name) {
        this.id = id;
        this.type_name = type_name;
    }

    public TPostType(int id, String type_imtg_url, String type_name) {
        this.id = id;
        this.type_imtg_url = type_imtg_url;
        this.type_name = type_name;
    }
}
