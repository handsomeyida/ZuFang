package com.jc.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/28 14:19
 * @Description:
 */
@Repository
public interface TNavigationMapper {

    List<JSONObject> listnavigation();

}
