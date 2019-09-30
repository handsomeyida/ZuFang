package com.jc.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/20 14:12
 * @Description:
 */
@Repository
public interface SysInformationMapper {
    //获取公告信息
    JSONObject loadInformation();
    //获取用户收到的消息(弹框)
    List<JSONObject> loadMsg(JSONObject jsonObject);
    //获取指定用户的信息详情
    List<JSONObject> loadUserMsg(JSONObject jsonObject);
    //获取指定用户的信息详情(有分页)
    List<JSONObject> listUserMsg(JSONObject jsonObject);
    //获取指定用户的信息详情(有分页)
    int countUserMsg(JSONObject jsonObject);
    //修改公告显示信息
    int saveWords(String content);
    //群发消息
    int insertAllInformation(JSONObject jsonObject);
    //像指定用户们发送信息
    int insertUserInformation(@Param("userId") String userId,
                              @Param("userIds") List<Integer> userIds,
                              @Param("content") String content,
                              @Param("createTime") Date createTime);
    //像指定用户发送信息
    int insertOneUserInformation( @Param("userId") String userId,
                                  @Param("userIds") String userIds,
                                  @Param("content") String content,
                                  @Param("createTime") Date createTime);
    //把消息设为已读
    int deleteMsg(JSONObject jsonObject);
    //获取所有系统通知消息
    List<JSONObject> listsysinfomation(JSONObject jsonObject);
    int countsysinfomation(JSONObject jsonObject);
}
