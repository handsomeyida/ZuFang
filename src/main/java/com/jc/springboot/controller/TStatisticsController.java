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
 * @Description:
 */
@RestController
@RequestMapping("/statistics")
public class TStatisticsController {

    @Resource
    TStatisticsService tStatisticsService;

    @RequestMapping("/listwxuser")
    public JSONObject listwxuser(){
        return tStatisticsService.listwxuser();
    }

    @RequestMapping("/listweeksresonate")
    public JSONObject listweeksresonate(){
        return tStatisticsService.listweeksresonate();
    }

    @RequestMapping("/listmonthsresonate")
    public JSONObject listmonthsresonate(){
        return tStatisticsService.listmonthsresonate();
    }

    @RequestMapping("/listweekscomment")
    public JSONObject listweekscomment(){
        return tStatisticsService.listweekscomment();
    }

    @RequestMapping("/listmonthscomment")
    public JSONObject listmonthscomment(){
        return tStatisticsService.listmonthscomment();
    }

    @RequestMapping("/listweekscollection")
    public JSONObject listweekscollection(){
        return tStatisticsService.listweekscollection();
    }

    @RequestMapping("/listmonthscollection")
    public JSONObject listmonthscollection(){
        return tStatisticsService.listmonthscollection();
    }

    @RequestMapping("/listweeksfans")
    public JSONObject listweeksfans(){
        return tStatisticsService.listweeksfans();
    }

    @RequestMapping("/listmonthsfans")
    public JSONObject listmonthsfans(){
        return tStatisticsService.listmonthsfans();
    }

    @RequestMapping("/listWeekMAXPriceCount")
    public JSONObject listWeekMAXPriceCount(@RequestBody JSONObject requestJson){
        return tStatisticsService.listWeekMAXPriceCount(requestJson);
    }

    @RequestMapping("/listWeekMINPriceCount")
    public JSONObject listWeekMINPriceCount(@RequestBody JSONObject requestJson){
        return tStatisticsService.listWeekMINPriceCount(requestJson);
    }

    @RequestMapping("/listMonthMAXPriceCount")
    public JSONObject listMonthMAXPriceCount(@RequestBody JSONObject requestJson){
        return tStatisticsService.listMonthMAXPriceCount(requestJson);
    }

    @RequestMapping("/listMonthMINPriceCount")
    public JSONObject listMonthMINPriceCount(@RequestBody JSONObject requestJson){
        return tStatisticsService.listMonthMINPriceCount(requestJson);
    }
}
