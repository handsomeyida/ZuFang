package com.jc.springboot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.dao.TStatisticsMapper;
import com.jc.springboot.service.TStatisticsService;
import com.jc.springboot.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/30 10:36
 * @Description:
 */
@Slf4j
@Service
public class TStatisticsServiceImpl implements TStatisticsService {

    @Resource
    TStatisticsMapper tStatisticsMapper;

    @Override
    public JSONObject listwxuser() {
        JSONObject info = new JSONObject();
        List<JSONObject> list = tStatisticsMapper.listwxuser();
        info.put("list", list);
        return LoginUtil.successJson(info);
    }

    @Override
    public JSONObject listweeksresonate() {
        JSONObject info = new JSONObject();
        List<JSONObject> list = tStatisticsMapper.listweeksresonate();
        info.put("list", list);
        return LoginUtil.successJson(info);
    }

    @Override
    public JSONObject listmonthsresonate() {
        JSONObject info = new JSONObject();
        List<JSONObject> list = tStatisticsMapper.listmonthsresonate();
        info.put("list", list);
        return LoginUtil.successJson(info);
    }

    @Override
    public JSONObject listweekscomment() {
        JSONObject info = new JSONObject();
        List<JSONObject> list = tStatisticsMapper.listweekscomment();
        info.put("list", list);
        return LoginUtil.successJson(info);
    }

    @Override
    public JSONObject listmonthscomment() {
        JSONObject info = new JSONObject();
        List<JSONObject> list = tStatisticsMapper.listmonthscomment();
        info.put("list", list);
        return LoginUtil.successJson(info);
    }

    @Override
    public JSONObject listweekscollection() {
        JSONObject info = new JSONObject();
        List<JSONObject> list = tStatisticsMapper.listweekscollection();
        info.put("list", list);
        return LoginUtil.successJson(info);
    }

    @Override
    public JSONObject listmonthscollection() {
        JSONObject info = new JSONObject();
        List<JSONObject> list = tStatisticsMapper.listmonthscollection();
        info.put("list", list);
        return LoginUtil.successJson(info);
    }

    @Override
    public JSONObject listweeksfans() {
        JSONObject info = new JSONObject();
        List<JSONObject> list = tStatisticsMapper.listweeksfans();
        info.put("list", list);
        return LoginUtil.successJson(info);
    }

    @Override
    public JSONObject listmonthsfans() {
        JSONObject info = new JSONObject();
        List<JSONObject> list = tStatisticsMapper.listmonthsfans();
        info.put("list", list);
        return LoginUtil.successJson(info);
    }

    @Override
    public JSONObject listWeekMAXPriceCount(JSONObject jsonObject) {
        int maxprice = tStatisticsMapper.loadmaxprice();
        jsonObject.put("MaxPrice", maxprice);
        List<JSONObject> list = tStatisticsMapper.listWeekMAXPriceCount(jsonObject);
        JSONObject info = new JSONObject();
        info.put("maxlist", list);
        return LoginUtil.successJson(info);
    }

    @Override
    public JSONObject listWeekMINPriceCount(JSONObject jsonObject) {
        int maxprice = tStatisticsMapper.loadmaxprice();
        jsonObject.put("MaxPrice", maxprice);
        List<JSONObject> list = tStatisticsMapper.listWeekMINPriceCount(jsonObject);
        JSONObject info = new JSONObject();
        info.put("minlist", list);
        return LoginUtil.successJson(info);
    }

    @Override
    public JSONObject listMonthMAXPriceCount(JSONObject jsonObject) {
        int maxprice = tStatisticsMapper.loadmaxprice();
        jsonObject.put("MaxPrice", maxprice);
        List<JSONObject> list = tStatisticsMapper.listMonthMAXPriceCount(jsonObject);
        JSONObject info = new JSONObject();
        info.put("maxlist", list);
        return LoginUtil.successJson(info);
    }

    @Override
    public JSONObject listMonthMINPriceCount(JSONObject jsonObject) {
        int maxprice = tStatisticsMapper.loadmaxprice();
        jsonObject.put("MaxPrice", maxprice);
        List<JSONObject> list = tStatisticsMapper.listMonthMINPriceCount(jsonObject);
        JSONObject info = new JSONObject();
        info.put("minlist", list);
        return LoginUtil.successJson(info);
    }
}
