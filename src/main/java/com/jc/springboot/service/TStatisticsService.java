package com.jc.springboot.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/30 10:36
 * @Description:
 */
public interface TStatisticsService {
    //获取微信用户的信息
    JSONObject listwxuser();
    //按周获取点赞统计
    JSONObject listweeksresonate();
    //按月获取点赞统计
    JSONObject listmonthsresonate();
    //按周获取评论统计
    JSONObject listweekscomment();
    //按月获取评论统计
    JSONObject listmonthscomment();
    //按周获取收藏统计
    JSONObject listweekscollection();
    //按月获取收藏统计
    JSONObject listmonthscollection();
    //按周获取关注统计
    JSONObject listweeksfans();
    //按月获取关注统计
    JSONObject listmonthsfans();
    //按周获取最大价位统计
    JSONObject listWeekMAXPriceCount(JSONObject jsonObject);
    //按周获取最小价位统计
    JSONObject listWeekMINPriceCount(JSONObject jsonObject);
    //按月获取最大价位统计
    JSONObject listMonthMAXPriceCount(JSONObject jsonObject);
    //按月获取最小价位统计
    JSONObject listMonthMINPriceCount(JSONObject jsonObject);
    //按周获取商圈帖子统计
    JSONObject listweekshopping();
    //按月获取商圈帖子统计
    JSONObject listmonthshopping();
    //按周获取地铁帖子统计
    JSONObject listweeksubway();
    //按月获取地铁帖子统计
    JSONObject listmonthsubway();
}
