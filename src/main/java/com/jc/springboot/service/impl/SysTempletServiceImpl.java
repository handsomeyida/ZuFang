package com.jc.springboot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.dao.SysTempletMapper;
import com.jc.springboot.service.SysTempletService;
import com.jc.springboot.util.ErrorEnum;
import com.jc.springboot.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/21 09:35
 * @Description:
 */
@Slf4j
@Service
public class SysTempletServiceImpl implements SysTempletService {

    @Resource
    SysTempletMapper templetMapper;
    //获取系统模板(分页, 没有公告模板)
    @Override
    public JSONObject listtemplet(JSONObject jsonObject) {
        LoginUtil.fillPageParam(jsonObject);
        JSONObject info = new JSONObject();
        int count = templetMapper.countTemplate(jsonObject);
        List<JSONObject> list = templetMapper.listtemplet(jsonObject);
        info.put("list", list);
        return LoginUtil.successPage(jsonObject, list, count);
    }
    //获取全部系统模板
    @Override
    public JSONObject listalltemplet(JSONObject jsonObject) {
        LoginUtil.fillPageParam(jsonObject);
        JSONObject info = new JSONObject();
        int count = templetMapper.countallTemplate(jsonObject);
        List<JSONObject> list = templetMapper.listalltemplet(jsonObject);
        info.put("list", list);
        return LoginUtil.successPage(jsonObject, list, count);
    }
    //添加系统模板
    @Override
    public JSONObject insertTemplate(JSONObject jsonObject) {
        int exist = templetMapper.loadExistTitle(jsonObject);
        if (exist > 0) {
            return LoginUtil.errorJson(ErrorEnum.E_10010);
        }
        templetMapper.insertTemplate(jsonObject);
        return LoginUtil.successJson();
    }
    //修改系统模板
    @Override
    public JSONObject updateTemplate(JSONObject jsonObject) {
        templetMapper.updateTemplate(jsonObject);
        return LoginUtil.successJson();
    }
    //删除系统模板
    @Override
    public JSONObject deleteTemplate(JSONObject jsonObject) {
        templetMapper.deleteTemplate(jsonObject);
        return LoginUtil.successJson();
    }
}
