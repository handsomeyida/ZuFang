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

    JSONObject loadInformation();

    List<JSONObject> loadMsg(JSONObject jsonObject);

    List<JSONObject> loadUserMsg(JSONObject jsonObject);

    List<JSONObject> listUserMsg(JSONObject jsonObject);

    int countUserMsg(JSONObject jsonObject);

    int saveWords(String content);

    int insertAllInformation(JSONObject jsonObject);
    //批量插入角色的权限
    int insertUserInformation(@Param("userId") String userId,
                              @Param("userIds") List<Integer> userIds,
                              @Param("content") String content,
                              @Param("createTime") Date createTime);

    int insertOneUserInformation( @Param("userId") String userId,
                                  @Param("userIds") String userIds,
                                  @Param("content") String content,
                                  @Param("createTime") Date createTime);

    int deleteMsg(JSONObject jsonObject);
}
