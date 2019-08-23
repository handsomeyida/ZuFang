package com.jc.springboot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/21 09:41
 * @Description:
 */
@Data
public class SysTemplet implements Serializable {

    private int id;

    private String title;

    private String content;

    private int isnotguide;

    public SysTemplet() {
    }

    public SysTemplet(String title, String content, int isnotguide) {
        this.title = title;
        this.content = content;
        this.isnotguide = isnotguide;
    }
}
