package com.jc.springboot.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/21 09:35
 * @Description:
 */
public interface SysTempletService {

    JSONObject listtemplet(JSONObject jsonObject);

    JSONObject listalltemplet(JSONObject jsonObject);

    JSONObject insertTemplate(JSONObject jsonObject);

    JSONObject updateTemplate(JSONObject jsonObject);

    JSONObject deleteTemplate(JSONObject jsonObject);

}
