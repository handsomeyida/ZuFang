package com.jc.springboot.dao;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.entity.TPostLabel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/23 14:38
 * @Description:
 */
@Repository
public interface TPostMapper {
    //获取所有帖子类型
    List<JSONObject> listType(JSONObject jsonObject);
    int countType(JSONObject jsonObject);
    //获取家用设备信息
    List<JSONObject> listhomelabels(JSONObject jsonObject);
    int counthomelabels(JSONObject jsonObject);
    //添加家用设备信息
    int inserthomelabels(TPostLabel tPostLabel);
    //修改家用设备信息
    int updatehomelabels(TPostLabel tPostLabel);
    //删除家用设备信息
    int deletehomelabels(JSONObject jsonObject);
    //获取热门商圈信息
    List<JSONObject> listshoplabels();
    int loadExistlabels(JSONObject jsonObject);
    //添加热门商圈信息
    int insertparentlabels(TPostLabel tPostLabel);
    int insertshoplabels(JSONObject jsonObject);
    //修改热门商圈信息
//    int updateshoplabels(JSONObject jsonObject);
    //删除热门商圈信息
    int deleteparentlabels(JSONObject jsonObject);
    int deleteshoplabels(JSONObject jsonObject);
    //获取地铁周边信息
    List<JSONObject> listsubwaylabels();
    //设置标签的排序号
    boolean updateIndex(@Param("listID") Integer listID,@Param("listIndex") Integer listIndex);
    //标签改变顺序后的排序
//    int updateChangeIndex(@Param("listID") Integer listID,@Param("listIndex") Integer listIndex);
}
