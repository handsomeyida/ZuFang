package com.jc.springboot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.dao.TAdvertBannerMapper;
import com.jc.springboot.service.TAdvertBannerService;
import com.jc.springboot.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019-9-10 11:06
 * @Description:
 */
@Slf4j
@Service
public class TAdvertBannerServiceImpl implements TAdvertBannerService {

    @Resource
    TAdvertBannerMapper tAdvertBannerMapper;
    //查询广告栏信息
    @Override
    public JSONObject listadvert(JSONObject jsonObject) {
        LoginUtil.fillPageParam(jsonObject);
        JSONObject info = new JSONObject();
        List<JSONObject> list = tAdvertBannerMapper.listadvert(jsonObject);
        int count = tAdvertBannerMapper.countadvert(jsonObject);
        info.put("list", list);
        return LoginUtil.successPage(jsonObject, list, count);
    }
    //删除广告栏信息
    @Override
    public JSONObject deleteadvert(JSONObject jsonObject) {
        tAdvertBannerMapper.deleteadvert(jsonObject);
        return LoginUtil.successJson();
    }
    //设置标签的排序号
    @Override
    public JSONObject updateIndex(JSONObject jsonObject) {
        List<Integer> listid = (List<Integer>) jsonObject.get("listID");
        List<Integer> listindex = (List<Integer>) jsonObject.get("listIndex");
        for (int i = 0; i < listid.size(); i++) {
            tAdvertBannerMapper.updateIndex(listid.get(i), listindex.get(i));
        }
        return LoginUtil.successJson();
    }
    //标签改变顺序后的排序
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public JSONObject updateChangeIndex(JSONObject jsonObject) {
        List<Integer> listid = (List<Integer>) jsonObject.get("listID");
        List<Integer> listindex = (List<Integer>) jsonObject.get("listIndex");
        int j = 0;
        for (int i = listid.size()-1; i >= 0 ; i--) {
            tAdvertBannerMapper.updateIndex(listid.get(j), listindex.get(i));
            j++;
        }
        return LoginUtil.successJson("success");
    }
}
