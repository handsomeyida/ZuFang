package com.jc.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/19 12:00
 * @Description: 广告栏实体类
 */
@Data
public class TAdvertBanner implements Serializable {

    private int id;

    private String advert_title;

    private String img_url;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sort_time;

    private int is_del;

    public TAdvertBanner() {
    }

    public TAdvertBanner(String advert_title, String img_url, Date create_time, Date sort_time, int is_del) {
        this.advert_title = advert_title;
        this.img_url = img_url;
        this.create_time = create_time;
        this.sort_time = sort_time;
        this.is_del = is_del;
    }
}
