package com.jc.springboot.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/21 09:29
 * @Description:
 */
public interface SysInformationService {

    JSONObject loadInformation();

    JSONObject loadMsg(JSONObject jsonObject);

    JSONObject loadUserMsg(JSONObject jsonObject);

    JSONObject listUserMsg(JSONObject jsonObject);

    JSONObject saveWords(JSONObject jsonObject);

    JSONObject insertAllInformation(JSONObject jsonObject);

    JSONObject insertInformation(JSONObject jsonObject);

    JSONObject deleteMsg(JSONObject jsonObject);
}
