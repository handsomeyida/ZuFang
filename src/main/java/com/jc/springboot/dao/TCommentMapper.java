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
    //删除指定评论
    int deletecomment(JSONObject jsonObject);
}
