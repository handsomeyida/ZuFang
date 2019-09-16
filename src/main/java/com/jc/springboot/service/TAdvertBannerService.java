package com.jc.springboot.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Auther: 帅气的达
 * @Date: 2019-9-10 11:05
 * @Description:
 */
public interface TAdvertBannerService {
    //查询广告栏信息
    JSONObject listadvert(JSONObject jsonObject);
    //删除广告
    JSONObject deleteadvert(JSONObject jsonObject);
    //设置标签的排序号
    JSONObject updateIndex(JSONObject jsonObject);
    //标签改变顺序后的排序
    JSONObject updateChangeIndex(JSONObject jsonObject);
}
