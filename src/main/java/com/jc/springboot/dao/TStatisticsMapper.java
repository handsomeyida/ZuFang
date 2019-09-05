package com.jc.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/30 10:25
 * @Description:
 */
@Repository
public interface TStatisticsMapper {
    //获取微信用户的信息
    List<JSONObject> listwxuser();
    //按周获取点赞统计
    List<JSONObject> listweeksresonate();
    //按月获取点赞统计
    List<JSONObject> listmonthsresonate();
    //按周获取评论统计
    List<JSONObject> listweekscomment();
    //按月获取评论统计
    List<JSONObject> listmonthscomment();
    //按周获取收藏统计
    List<JSONObject> listweekscollection();
    //按月获取收藏统计
    List<JSONObject> listmonthscollection();
    //按周获取关注统计
    List<JSONObject> listweeksfans();
    //按月获取关注统计
    List<JSONObject> listmonthsfans();
    //获取所有价格中的最高价格
    int loadmaxprice();
    //按周获取最大价位统计
    List<JSONObject> listWeekMAXPriceCount(JSONObject jsonObject);
    //按周获取最小价位统计
    List<JSONObject> listWeekMINPriceCount(JSONObject jsonObject);
    //按月获取最大价位统计
    List<JSONObject> listMonthMAXPriceCount(JSONObject jsonObject);
    //按月获取最小价位统计
    List<JSONObject> listMonthMINPriceCount(JSONObject jsonObject);
    //按周获取商圈帖子统计
    List<JSONObject> listweekshopping();
    //按月获取商圈帖子统计
    List<JSONObject> listmonthshopping();
    //按周获取地铁帖子统计
    List<JSONObject> listweeksubway();
    //按月获取地铁帖子统计
    List<JSONObject> listmonthsubway();
}
