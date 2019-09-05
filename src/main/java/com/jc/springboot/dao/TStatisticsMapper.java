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

    List<JSONObject> listwxuser();

    List<JSONObject> listweeksresonate();

    List<JSONObject> listmonthsresonate();

    List<JSONObject> listweekscomment();

    List<JSONObject> listmonthscomment();

    List<JSONObject> listweekscollection();

    List<JSONObject> listmonthscollection();

    List<JSONObject> listweeksfans();

    List<JSONObject> listmonthsfans();

    int loadmaxprice();

    List<JSONObject> listWeekMAXPriceCount(JSONObject jsonObject);

    List<JSONObject> listWeekMINPriceCount(JSONObject jsonObject);

    List<JSONObject> listMonthMAXPriceCount(JSONObject jsonObject);

    List<JSONObject> listMonthMINPriceCount(JSONObject jsonObject);
}
