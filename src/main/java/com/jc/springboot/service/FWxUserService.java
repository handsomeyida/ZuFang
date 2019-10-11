package com.jc.springboot.service;

import com.jc.springboot.entity.FBaseWxuserTypeLabel;

import java.util.Date;
import java.util.List;

public interface FWxUserService {
    //显示全部微信用户信息
    List<FBaseWxuserTypeLabel> listWxUserF(String page
            ,String limit
            ,String wxuserCreateTime
            ,String wxGender
            ,String wxCountry
            ,String wxNickName);
    //统计微信用户表数据总条数(包含模糊查询后的总条数)
    int countWxUserF(String wxuserCreateTime,String wxGender,String wxCountry,String wxNickName);

    //查询某微信用户所有的收藏信息(收藏帖子)
    List<FBaseWxuserTypeLabel> listCollectionF(String page, String limit, String wxuserId);
    //统计某微信用户所有的收藏信息(收藏帖子)
    int countCollectionF(String wxuserId);

    //查询某微信用户所有的关注列表
    List<FBaseWxuserTypeLabel> listFollowF(String page, String limit, String wxuserId);
    //统计某微信用户所有的关注列表
    int countFollowF(String wxuserId);

    //t_wx_user表的修改
    Boolean updateWxUserF(String wxAvatarUrl,   String wxNickName,
                        String wxGender,        String wxCountry,
                        String wxProvince,      String wxCity,
                        String devFans,         Date wxuserCreateTime,
                        Date updateTime,        String isDelWxuser,
                        String wxuserId);

    //限制微信用户，修改t_wx_user表的isDelWxuser
    Boolean updateIsDelWxuserF(String wxuserId);
    //取消限制微信用户
    Boolean updateIsDelWxuser0F(String wxuserId);

    //查询所有消息模板的信息
    List<FBaseWxuserTypeLabel> listTemplateF();
    //t_sys_information表的添加
    Boolean insertSysInformationF(String wxuserId, String sysInformationContent, String sysInformationCreateTime);
}
