package com.jc.springboot.dao;

import com.jc.springboot.entity.FBaseWxuserTypeLabel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FWxUserMapper {
    //显示全部微信用户信息
    List<FBaseWxuserTypeLabel> listWxUserF(
            @Param("start") int start, @Param("end") int end
            ,@Param("wxuserCreateTime") String wxuserCreateTime
            ,@Param("wxGender") String wxGender
            ,@Param("wxCountry") String wxCountry
            ,@Param("wxNickName") String wxNickName);
    //统计微信用户表数据总条数(包含模糊查询后的总条数)
    int countWxUserF(String wxuserCreateTime,String wxGender,String wxCountry,String wxNickName);

    //查询某微信用户所有的收藏信息(收藏帖子)
    List<FBaseWxuserTypeLabel> listCollectionF(@Param("start") int start, @Param("end") int end, @Param("collectionUserId") String wxuserId);
    //统计某微信用户所有的收藏信息(收藏帖子)
    int countCollectionF(@Param("collectionUserId") String wxuserId);

    //查询某微信用户所有的关注列表
    List<FBaseWxuserTypeLabel> listFollowF(@Param("start") int start, @Param("end") int end, @Param("followFanId") String wxuserId);
    //统计某微信用户所有的关注列表
    int countFollowF(@Param("followFanId") String wxuserId);

    //t_wx_user表的修改
    Boolean updateWxUserF(@Param("wxAvatarUrl") String wxAvatarUrl,         @Param("wxNickName") String wxNickName,
                        @Param("wxGender") String wxGender,               @Param("wxCountry") String wxCountry,
                        @Param("wxProvince") String wxProvince,           @Param("wxCity") String wxCity,
                        @Param("devFans") String devFans,                 @Param("wxuserCreateTime") Date wxuserCreateTime,
                        @Param("updateTime") Date updateTime,           @Param("isDelWxuser") String isDelWxuser,
                        @Param("wxuserId") String wxuserId);

    //限制微信用户，修改t_wx_user表的isDelWxuser
    Boolean updateIsDelWxuserF(@Param("wxuserId") String wxuserId);
    //取消限制微信用户
    Boolean updateIsDelWxuser0F(@Param("wxuserId") String wxuserId);

    //查询所有消息模板的信息
    List<FBaseWxuserTypeLabel> listTemplateF();
    //t_sys_information表的添加
    Boolean insertSysInformationF(@Param("wxuserId") String wxuserId, @Param("sysInformationContent") String sysInformationContent, @Param("sysInformationCreateTime") String sysInformationCreateTime);
}
