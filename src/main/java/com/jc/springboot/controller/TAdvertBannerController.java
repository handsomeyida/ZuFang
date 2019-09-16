package com.jc.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.service.TAdvertBannerService;
import com.jc.springboot.util.LoginUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: 帅气的达
 * @Date: 2019-9-10 11:08
 * @Description: 广告栏
 */
@RestController
@RequestMapping("/advert")
public class TAdvertBannerController {

    @Resource
    TAdvertBannerService tAdvertBannerService;
    //查询广告栏信息
    @RequestMapping("/listadvert")
    public JSONObject listType(HttpServletRequest request){
        return tAdvertBannerService.listadvert(LoginUtil.request2Json(request));
    }
    //删除广告
    @RequestMapping("/deleteadvert")
    public JSONObject deleteadvert(@RequestBody JSONObject requestJson){
        return tAdvertBannerService.deleteadvert(requestJson);
    }
    //设置标签的排序号
    @RequestMapping("/updateIndex")
    public JSONObject updateIndex(@RequestBody JSONObject requestJson){
        return tAdvertBannerService.updateIndex(requestJson);
    }
    //标签改变顺序后的排序
    @RequestMapping("/updateChangeIndex")
    public JSONObject updateChangeIndex(@RequestBody JSONObject requestJson){
        return tAdvertBannerService.updateChangeIndex(requestJson);
    }

}
