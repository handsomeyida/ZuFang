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

    List<JSONObject> listtemplet(JSONObject jsonObject);

    int countTemplate(JSONObject jsonObject);

    //检查模板标题是否已存在
    int loadExistTitle(JSONObject jsonObject);

    int insertTemplate(JSONObject jsonObject);

    int updateTemplate(JSONObject jsonObject);

    int deleteTemplate(JSONObject jsonObject);
}
