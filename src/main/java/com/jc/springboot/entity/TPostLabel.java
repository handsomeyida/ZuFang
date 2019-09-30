package com.jc.springboot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/27 13:59
 * @Description: 标签实体类
 */
@Data
public class TPostLabel implements Serializable {

    private int id;

    private int parent_id;

    private String content;

    private int is_del;

    private String type_img_url;

    public TPostLabel() {

    }

    public TPostLabel(int parent_id, String content) {
        this.parent_id = parent_id;
        this.content = content;
    }

    public TPostLabel(int parent_id, String content, int is_del, String type_img_url) {
        this.parent_id = parent_id;
        this.content = content;
        this.is_del = is_del;
        this.type_img_url = type_img_url;
    }

    public TPostLabel(int id, String content, String type_img_url) {
        this.id = id;
        this.content = content;
        this.type_img_url = type_img_url;
    }
}
