package com.jc.springboot.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/30 10:36
 * @Description:
 */
public interface TStatisticsService {

    JSONObject listwxuser();

    JSONObject listweeksresonate();

    JSONObject listmonthsresonate();

    JSONObject listweekscomment();

    JSONObject listmonthscomment();

    JSONObject listweekscollection();

    JSONObject listmonthscollection();

    JSONObject listweeksfans();

    JSONObject listmonthsfans();

    JSONObject listWeekMAXPriceCount(JSONObject jsonObject);

    JSONObject listWeekMINPriceCount(JSONObject jsonObject);

    JSONObject listMonthMAXPriceCount(JSONObject jsonObject);

    JSONObject listMonthMINPriceCount(JSONObject jsonObject);
}
