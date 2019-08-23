package com.jc.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/23 14:38
 * @Description:
 */
@Repository
public interface PostMapper {

    List<JSONObject> listType(JSONObject jsonObject);

    int countType(JSONObject jsonObject);
}
