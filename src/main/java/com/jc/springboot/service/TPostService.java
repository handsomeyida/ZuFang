package com.jc.springboot.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/23 14:34
 * @Description:
 */
public interface TPostService {
    //获取所有帖子类型
    JSONObject listType(JSONObject jsonObject);
    //获取家用设备信息
    JSONObject listhomelabels(JSONObject jsonObject);
    //添加家用设备信息
    JSONObject inserthomelabels(JSONObject jsonObject);
    //修改家用设备信息
    JSONObject updatehomelabels(JSONObject jsonObject);
    //删除家用设备信息
    JSONObject deletehomelabels(JSONObject jsonObject);
    //获取热门商圈信息
    JSONObject listshoplabels();
    //添加热门商圈信息
    JSONObject insertshoplabels(JSONObject jsonObject);
    //修改热门商圈信息
    JSONObject updateshoplabels(JSONObject jsonObject);
    //删除热门商圈信息
    JSONObject deleteonelabels(JSONObject jsonObject);
    //删除热门商圈信息
    JSONObject deletealllabels(JSONObject jsonObject);
    //获取地铁周边信息
    JSONObject listsubwaylabels();
    //添加地铁周边信息
    JSONObject insertsubwaylabels(JSONObject jsonObject);

}
