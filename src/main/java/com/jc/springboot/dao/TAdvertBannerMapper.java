package com.jc.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.entity.TAdvertBanner;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/19 12:05
 * @Description:
 */
@Repository
public interface TAdvertBannerMapper {
    //添加广告栏信息
    Boolean insert(TAdvertBanner tAdvertBanner);
    //查询广告栏信息
    List<JSONObject> listadvert(JSONObject jsonObject);
    int countadvert(JSONObject jsonObject);
    //删除广告
    int deleteadvert(JSONObject jsonObject);
    //修改广告
    int updateadvert(TAdvertBanner advertBanner);
    //设置广告的排序号
    int updateIndex(@Param("listID") Integer listID, @Param("listIndex") Integer listIndex);

}
