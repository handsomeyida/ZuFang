package com.jc.springboot.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/23 14:34
 * @Description:
 */
public interface TPostService {

    JSONObject listType(JSONObject jsonObject);

    JSONObject listhomelabels(JSONObject jsonObject);

    JSONObject inserthomelabels(JSONObject jsonObject);

    JSONObject updatehomelabels(JSONObject jsonObject);

    JSONObject deletehomelabels(JSONObject jsonObject);

    JSONObject listshoplabels();

    JSONObject insertshoplabels(JSONObject jsonObject);

    JSONObject updateshoplabels(JSONObject jsonObject);

    JSONObject deleteonelabels(JSONObject jsonObject);

    JSONObject deletealllabels(JSONObject jsonObject);

    JSONObject listsubwaylabels();

    JSONObject insertsubwaylabels(JSONObject jsonObject);


}
