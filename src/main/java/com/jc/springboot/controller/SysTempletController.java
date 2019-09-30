package com.jc.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.service.SysTempletService;
import com.jc.springboot.util.LoginUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/21 09:29
 * @Description:
 */
@RestController
@RequestMapping("/templet")
public class SysTempletController {

    @Resource
    SysTempletService templetService;
    //获取系统模板(分页, 没有公告模板)
    @RequiresPermissions("guide:list")
    @RequestMapping("/listtemplet")
    public JSONObject listtemplet(HttpServletRequest request){
        return templetService.listtemplet(LoginUtil.request2Json(request));
    }
    //获取全部系统模板
    @RequiresPermissions("guide:list")
    @RequestMapping("/listalltemplet")
    public JSONObject listalltemplet(HttpServletRequest request){
        return templetService.listalltemplet(LoginUtil.request2Json(request));
    }
    //添加系统模板
    @RequiresPermissions("template:add")
    @RequestMapping("/addTemplate")
    public JSONObject insertTemplate(@RequestBody JSONObject requestJson){
//        System.out.println(requestJson);
        LoginUtil.hasAllRequired(requestJson, "TITLE, CONTENT");
        return templetService.insertTemplate(requestJson);
    }
    //修改系统模板
    @RequiresPermissions("template:update")
    @RequestMapping("/updateTemplate")
    public JSONObject updateTemplate(@RequestBody JSONObject requestJson){
//        System.out.println(requestJson);
        LoginUtil.hasAllRequired(requestJson, "TITLE, CONTENT");
        return templetService.updateTemplate(requestJson);
    }
    //删除系统模板
    @RequiresPermissions("template:delete")
    @RequestMapping("/deleteTemplate")
    public JSONObject deleteTemplate(@RequestBody JSONObject requestJson){
//        System.out.println(requestJson);
        LoginUtil.hasAllRequired(requestJson, "TITLE, CONTENT");
        return templetService.deleteTemplate(requestJson);
    }

}
