package com.jc.springboot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jc.springboot.dao.TCommentMapper;
import com.jc.springboot.service.TCommentService;
import com.jc.springboot.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/9/6 10:46
 * @Description:
 */
@Slf4j
@Service
public class TCommentServiceImpl implements TCommentService {

    @Resource
    TCommentMapper tCommentMapper;
    //获取指定贴子的所有评论
    @Override
    public JSONObject listcomment(JSONObject jsonObject) {
        LoginUtil.fillPageParam(jsonObject);
        JSONObject info = new JSONObject();
        int count = tCommentMapper.countcomment(jsonObject);
        List<JSONObject> list = tCommentMapper.listcomment(jsonObject);
        info.put("list", list);
        return LoginUtil.successPage(jsonObject, list, count);
    }
    //获取用户收藏的帖子
    @Override
    public JSONObject listcollectionbase(JSONObject jsonObject) {
        LoginUtil.fillPageParam(jsonObject);
        JSONObject info = new JSONObject();
        int count = tCommentMapper.countcollectionbase(jsonObject);
        List<JSONObject> list = tCommentMapper.listcollectionbase(jsonObject);
        info.put("list", list);
        return LoginUtil.successPage(jsonObject, list, count);
    }
    //删除指定评论
    @Override
    public JSONObject deletecomment(JSONObject jsonObject) {
        tCommentMapper.deletecomment(jsonObject);
        return LoginUtil.successJson();
    }
    //取消收藏
    @Override
    public JSONObject deletecollectionbase(JSONObject jsonObject) {
        tCommentMapper.deletecollectionbase(jsonObject);
        return LoginUtil.successJson();
    }
    //设置禁言
    @Override
    public JSONObject insertlimit(JSONObject jsonObject) {
        String end = (String)jsonObject.get("end");
        Date endtime = loadendtime(Integer.parseInt(end));
        jsonObject.put("endtime", endtime);
        jsonObject.put("restrict", 1);
        int status = tCommentMapper.loadlimit(jsonObject);
        if (status > 0) {
            tCommentMapper.updatelimit(jsonObject);
        } else {
            tCommentMapper.insertlimit(jsonObject);
        }
        return LoginUtil.successJson();
    }
    //判断禁言是否到期
    @Override
    public JSONObject loadendtime(JSONObject jsonObject) {
        JSONObject jsonObject2 = tCommentMapper.loadendtime(jsonObject);
        String endtime = null;
        try {
            endtime = jsonObject2.getString("endtime");
        } catch (Exception e) {
            JSONObject info = new JSONObject();
            info.put("success", "success");
            return LoginUtil.successJson(info);
        }
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
        String format = sf.format(date);
        boolean status = comparedate(endtime, format);
        if (status) {
            return LoginUtil.successJson(jsonObject2);
        }
        return LoginUtil.successJson();
    }
    //禁言结束时间
    private Date loadendtime(int time){
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, time);//若想把日期向后推一天为正数,前一天改为负数
        date = calendar.getTime();
        return date;
    }
    //判断禁言是否结束
    public boolean comparedate(String date1, String date2){
        //将字符串格式的日期格式化
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
        try {
            //将日期转成Date对象作比较
            Date fomatDate1=sdf.parse(date1);
            Date fomatDate2=sdf.parse(date2);
            //比较两个日期
            int result=fomatDate2.compareTo(fomatDate1);
            //如果日期相等返回0
            if(result<=0){
                //小于等于0，参数date1就是在date2之后或相等
                return true;
            }else{
                //大于0，参数date1就是在date2之前
                return false;
            }
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return false;
    }
}
