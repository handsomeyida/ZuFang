package com.jc.springboot.controller;

import com.jc.springboot.entity.FBaseWxuserTypeLabel;
import com.jc.springboot.service.FPostBaseService;
import com.jc.springboot.service.FWxUserService;
import com.jc.springboot.util.model.response.IResult;
import com.jc.springboot.util.model.response.PageResultBean;
import com.jc.springboot.util.model.response.ResultBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/WxUser")
public class FWxUserController {

    @Resource
    FWxUserService fWxUserService;

    //查询所有微信用户信息
    @RequestMapping("/listWxUserF")
    public IResult listWxUserF(String page
                               ,String limit
                               ,String wxuserCreateTime
                               ,String wxGender
                               ,String wxCountry
                               ,String wxNickName){
        return new PageResultBean<Collection<FBaseWxuserTypeLabel>>(fWxUserService.listWxUserF(page,limit,wxuserCreateTime,wxGender,wxCountry,wxNickName),fWxUserService.countWxUserF(wxuserCreateTime,wxGender,wxCountry,wxNickName));
    }
    //查询某微信用户所有的收藏信息(收藏帖子)
    @RequestMapping("/listCollectionF")
    public IResult listCollectionF(String page, String limit, String wxuserId){
        return new PageResultBean<Collection<FBaseWxuserTypeLabel>>(fWxUserService.listCollectionF(page,limit,wxuserId),fWxUserService.countCollectionF(wxuserId));
    }

    //查询某微信用户所有的关注列表
    @RequestMapping("/listFollowF")
    public IResult listFollowF(String page, String limit, String wxuserId){
        return new PageResultBean<Collection<FBaseWxuserTypeLabel>>(fWxUserService.listFollowF(page,limit,wxuserId),fWxUserService.countFollowF(wxuserId));
    }

    //t_wx_user表的修改
    @RequestMapping("/updateWxUserF")
    public IResult updateWxUserF(@RequestBody FBaseWxuserTypeLabel updateWxUser){
        String wxuserId = updateWxUser.getWxuserId();
        String wxAvatarUrl = updateWxUser.getWxAvatarUrl();
        String wxNickName = updateWxUser.getWxNickName();
        String wxGender = updateWxUser.getWxGender();
        String wxCountry = updateWxUser.getWxCountry();
        String wxProvince = updateWxUser.getWxProvince();
        String wxCity = updateWxUser.getWxCity();
        String devFans = updateWxUser.getDevFans();
        Date wxuserCreateTime = updateWxUser.getWxuserCreateTime();
        Date updateTime = updateWxUser.getUpdateTime();
        String isDelWxuser = updateWxUser.getIsDelWxuser();
        return new ResultBean<Boolean>(fWxUserService.updateWxUserF(wxAvatarUrl,wxNickName,
                                                                    wxGender,wxCountry,wxProvince,
                                                                    wxCity,devFans,wxuserCreateTime,
                                                                    updateTime,isDelWxuser,wxuserId));
    }

    //限制微信用户，修改t_wx_user表的isDelWxuser
    @RequestMapping("/updateIsDelWxuserF")
    public IResult updateIsDelWxuserF(String wxuserId){
        return new ResultBean<Boolean>(fWxUserService.updateIsDelWxuserF(wxuserId));
    }
    //取消限制微信用户
    @RequestMapping("/updateIsDelWxuser0F")
    public IResult updateIsDelWxuser0F(String wxuserId){
        return new ResultBean<Boolean>(fWxUserService.updateIsDelWxuser0F(wxuserId));
    }

    //查询所有消息模板的信息
    @RequestMapping("/listTemplateF")
    public IResult listTemplateF(){
        return new ResultBean<Collection<FBaseWxuserTypeLabel>>(fWxUserService.listTemplateF());
    }
    //t_sys_information表的添加
    @RequestMapping("/insertSysInformationF")
    public IResult insertSysInformationF(String wxuserId, String sysInformationContent){
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysInformationCreateTime = time.format(new Date());
        return new ResultBean<Boolean>(fWxUserService.insertSysInformationF(wxuserId, sysInformationContent, sysInformationCreateTime));
    }

}
