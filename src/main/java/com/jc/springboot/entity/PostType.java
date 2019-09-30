package com.jc.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/23 16:12
 * @Description:
 */
@Data
public class PostType implements Serializable {
    private List<FBaseWxuserTypeLabel> listPostBaseF;

    private int id;

    private String type_imtg_url;

    private String type_name;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    private int is_del;

    public PostType() {
    }

    public PostType(int id, String type_name) {
        this.id = id;
        this.type_name = type_name;
    }

    public PostType(int id, String type_imtg_url, String type_name) {
        this.id = id;
        this.type_imtg_url = type_imtg_url;
        this.type_name = type_name;
    }
}
