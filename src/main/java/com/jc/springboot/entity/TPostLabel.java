package com.jc.springboot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/27 13:59
 * @Description:
 */
@Data
public class TPostLabel implements Serializable {

    private int id;

    private int parent_id;

    private String content;

    private int is_del;

    public TPostLabel() {

    }

    public TPostLabel(int parent_id, String content) {
        this.parent_id = parent_id;
        this.content = content;
    }
}
