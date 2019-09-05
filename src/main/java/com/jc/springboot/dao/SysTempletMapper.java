package com.jc.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/21 09:37
 * @Description:
 */
@Repository
public interface SysTempletMapper {
    //获取系统模板(分页, 没有公告模板)
    List<JSONObject> listtemplet(JSONObject jsonObject);
    int countTemplate(JSONObject jsonObject);
    //获取全部系统模板
    List<JSONObject> listalltemplet(JSONObject jsonObject);
    int countallTemplate(JSONObject jsonObject);
    //检查模板标题是否已存在
    int loadExistTitle(JSONObject jsonObject);
    //添加系统模板
    int insertTemplate(JSONObject jsonObject);
    //修改系统模板
    int updateTemplate(JSONObject jsonObject);
    //删除系统模板
    int deleteTemplate(JSONObject jsonObject);
}
