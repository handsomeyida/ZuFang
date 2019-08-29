package com.jc.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.entity.TPostLabel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/23 14:38
 * @Description:
 */
@Repository
public interface TPostMapper {

    List<JSONObject> listType(JSONObject jsonObject);

    int countType(JSONObject jsonObject);

    List<JSONObject> listhomelabels(JSONObject jsonObject);

    int counthomelabels(JSONObject jsonObject);

    int inserthomelabels(JSONObject jsonObject);

    int updatehomelabels(JSONObject jsonObject);

    int deletehomelabels(JSONObject jsonObject);

    List<JSONObject> listshoplabels();

    int loadExistlabels(JSONObject jsonObject);

    int insertparentlabels(TPostLabel tPostLabel);
    int insertshoplabels(JSONObject jsonObject);

    int updateshoplabels(JSONObject jsonObject);

    int deleteparentlabels(JSONObject jsonObject);
    int deleteshoplabels(JSONObject jsonObject);

    List<JSONObject> listsubwaylabels();
}
