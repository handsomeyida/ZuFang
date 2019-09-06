package com.jc.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: 帅气的达
 * @Date: 2019/9/6 10:21
 * @Description: 评论实体类
 */
@Data
public class TComment implements Serializable {

    private int id;

    private int postId;

    private int startname;

    private String receiveId;

    private String content;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private int isdel;

    public TComment() {
    }
}
