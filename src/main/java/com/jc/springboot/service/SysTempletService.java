package com.jc.springboot.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/21 09:35
 * @Description:
 */
public interface SysTempletService {
    //获取系统模板(分页, 没有公告模板)
    JSONObject listtemplet(JSONObject jsonObject);
    //获取全部系统模板
    JSONObject listalltemplet(JSONObject jsonObject);
    //添加系统模板
    JSONObject insertTemplate(JSONObject jsonObject);
    //修改系统模板
    JSONObject updateTemplate(JSONObject jsonObject);
    //删除系统模板
    JSONObject deleteTemplate(JSONObject jsonObject);

}
