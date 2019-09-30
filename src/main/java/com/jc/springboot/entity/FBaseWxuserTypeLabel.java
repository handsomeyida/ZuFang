package com.jc.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class FBaseWxuserTypeLabel implements Serializable {
    private List<String> listCheckNavName;//已被选
    private List<String> listCheckNavName1;//未被选
    private List<String> listCheckLabelName;//标签已被选
    private List<String> listCheckLabelName1;//标签未被选

    //t_post_base表
    private String postId;
    private String userId;
    private String typeId;
    private String content;
    //最小价格
    private String minPrice;
    //最大价格
    private String maxPrice;
    private String phone;
    private String address;
    //标签
    private String labels;
    //真实浏览量
    private String totalBrowse;
    //真实点赞数量
    private String totalResonate;
    //偏移的浏览量
    private String devBrowse;
    //偏移的点赞量
    private String devResonate;
    //活跃时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activeTime;
    //创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date baseCreateTime;
    //是否下架
    private String isLowerShelf;
    //是否删除
    private String isDelBase;
    //总收藏
    private String totalCollection;
    //总关注
    private String totalComments;
    //是否有中介费
    private String fee;
    //是否为地铁周边
    private String isSub;
    //是否为商圈附近
    private String isMall;

    //t_wx_user表
    private String wxuserId;
    //微信openId
    private String openId;
    //微信唯一标示
    private String unionId;
    //微信用户头像图片路径
    private String wxAvatarUrl;
    //微信名称
    private String wxNickName;
    //性别
    private String wxGender;
    //市
    private String wxCity;
    //省
    private String wxProvince;
    //国家
    private String wxCountry;
    //粉丝偏移量
    private String devFans;
    //创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date wxuserCreateTime;
    //更新时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    //是否删除
    private String isDelWxuser;
    //简介
    private String summary;

    //t_post_type表
    private String id;
    private String typeImtgUrl;
    private String typeName;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String isDelType;

    //t_post_label表
    private String labelId;
    //标签的父id
    private String parentId;
    private String labelContent;
    private String type;
    private String isDelLabel;
    private String typeImgUrl;

    //t_post_base_label关系表
    private String postBaseLabelId;
    //PostBase的Id
    private String postBaseId;
    //PostLabel的Id
    private String postLabelId;
    private String postBaseLabelIsDel;

    //t_navigation_top表
    private String topId;
    //导航栏ID
    private String navigationTopId;
    private String topPostId;
    //排序时间，用来排序使用
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sortTime;
    //创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date topCreateTime;
    private String topIsDel;

    //t_navigation表
    private String navigationId;
    private String title;
    private String maxTopNum;
    private String navigationCreateTime;
    private String navigationIsDel;
}
