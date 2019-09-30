package com.jc.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/9/6 10:43
 * @Description:
 */
@Repository
public interface TCommentMapper {
    //获取指定贴子的所有评论
    List<JSONObject> listcomment(JSONObject jsonObject);
    int countcomment(JSONObject jsonObject);
    //获取用户收藏的贴子
    List<JSONObject> listcollectionbase(JSONObject jsonObject);
    int countcollectionbase(JSONObject jsonObject);
    //删除指定评论
    int deletecomment(JSONObject jsonObject);
    //取消收藏
    int deletecollectionbase(JSONObject jsonObject);
    //查询用户有没有限制过
    int loadlimit(JSONObject jsonObject);
    //添加用户限制
    int insertlimit(JSONObject jsonObject);
    //修改用户限制
    int updatelimit(JSONObject jsonObject);
    //获取禁言到期时间
    JSONObject loadendtime(JSONObject jsonObject);

}
