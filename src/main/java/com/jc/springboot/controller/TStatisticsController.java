package com.jc.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.service.TStatisticsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/30 10:22
 * @Description: 统计模块
 */
@RestController
@RequestMapping("/statistics")
public class TStatisticsController {

    @Resource
    TStatisticsService tStatisticsService;
    //获取微信用户的信息
    @RequestMapping("/listwxuser")
    public JSONObject listwxuser(){
        return tStatisticsService.listwxuser();
    }
    //按周获取点赞统计
    @RequestMapping("/listweeksresonate")
    public JSONObject listweeksresonate(){
        return tStatisticsService.listweeksresonate();
    }
    //按月获取点赞统计
    @RequestMapping("/listmonthsresonate")
    public JSONObject listmonthsresonate(){
        return tStatisticsService.listmonthsresonate();
    }
    //按周获取评论统计
    @RequestMapping("/listweekscomment")
    public JSONObject listweekscomment(){
        return tStatisticsService.listweekscomment();
    }
    //按月获取评论统计
    @RequestMapping("/listmonthscomment")
    public JSONObject listmonthscomment(){
        return tStatisticsService.listmonthscomment();
    }
    //按周获取收藏统计
    @RequestMapping("/listweekscollection")
    public JSONObject listweekscollection(){
        return tStatisticsService.listweekscollection();
    }
    //按月获取收藏统计
    @RequestMapping("/listmonthscollection")
    public JSONObject listmonthscollection(){
        return tStatisticsService.listmonthscollection();
    }
    //按周获取关注统计
    @RequestMapping("/listweeksfans")
    public JSONObject listweeksfans(){
        return tStatisticsService.listweeksfans();
    }
    //按月获取关注统计
    @RequestMapping("/listmonthsfans")
    public JSONObject listmonthsfans(){
        return tStatisticsService.listmonthsfans();
    }
    //按周获取最大价位统计
    @RequestMapping("/listWeekMAXPriceCount")
    public JSONObject listWeekMAXPriceCount(@RequestBody JSONObject requestJson){
        return tStatisticsService.listWeekMAXPriceCount(requestJson);
    }
    //按周获取最小价位统计
    @RequestMapping("/listWeekMINPriceCount")
    public JSONObject listWeekMINPriceCount(@RequestBody JSONObject requestJson){
        return tStatisticsService.listWeekMINPriceCount(requestJson);
    }
    //按月获取最大价位统计
    @RequestMapping("/listMonthMAXPriceCount")
    public JSONObject listMonthMAXPriceCount(@RequestBody JSONObject requestJson){
        return tStatisticsService.listMonthMAXPriceCount(requestJson);
    }
    //按月获取最小价位统计
    @RequestMapping("/listMonthMINPriceCount")
    public JSONObject listMonthMINPriceCount(@RequestBody JSONObject requestJson){
        return tStatisticsService.listMonthMINPriceCount(requestJson);
    }
    //按周获取商圈帖子统计
    @RequestMapping("/listweekshopping")
    public JSONObject listweekshopping(){
        return tStatisticsService.listweekshopping();
    }
    //按月获取商圈帖子统计
    @RequestMapping("/listmonthshopping")
    public JSONObject listmonthshopping(){
        return tStatisticsService.listmonthshopping();
    }
    //按周获取地铁帖子统计
    @RequestMapping("/listweeksubway")
    public JSONObject listweeksubway(){
        return tStatisticsService.listweeksubway();
    }
    //按月获取地铁帖子统计
    @RequestMapping("/listmonthsubway")
    public JSONObject listmonthsubway(){
        return tStatisticsService.listmonthsubway();
    }
}
