package com.jc.springboot.service.impl;


import com.jc.springboot.dao.FPostBaseMapper;
import com.jc.springboot.dao.FWxUserMapper;
import com.jc.springboot.entity.FBaseWxuserTypeLabel;
import com.jc.springboot.entity.FPageRange;
import com.jc.springboot.service.FPostBaseService;
import com.jc.springboot.service.FWxUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class FWxUserServiceImpl implements FWxUserService {

    @Resource
    FWxUserMapper fWxUserMapper;


    @Override
    public List<FBaseWxuserTypeLabel> listWxUserF(String page
                                                ,String limit
                                                ,String wxuserCreateTime
                                                ,String wxGender
                                                ,String wxCountry
                                                ,String wxNickName) {
        FPageRange pageRange = new FPageRange(page,limit);
        return fWxUserMapper.listWxUserF(pageRange.getStart(),pageRange.getEnd(),wxuserCreateTime,wxGender,wxCountry,wxNickName);
    }

    @Override
    public int countWxUserF(String wxuserCreateTime,String wxGender,String wxCountry,String wxNickName) {
        return fWxUserMapper.countWxUserF(wxuserCreateTime,wxGender,wxCountry,wxNickName);
    }

    @Override
    public List<FBaseWxuserTypeLabel> listCollectionF(String page, String limit, String wxuserId) {
        FPageRange pageRange = new FPageRange(page,limit);
        return fWxUserMapper.listCollectionF(pageRange.getStart(),pageRange.getEnd(),wxuserId);
    }

    @Override
    public int countCollectionF(String wxuserId) {
        return fWxUserMapper.countCollectionF(wxuserId);
    }

    @Override
    public List<FBaseWxuserTypeLabel> listFollowF(String page, String limit, String wxuserId) {
        FPageRange pageRange = new FPageRange(page,limit);
        return fWxUserMapper.listFollowF(pageRange.getStart(),pageRange.getEnd(),wxuserId);
    }

    @Override
    public int countFollowF(String wxuserId) {
        return fWxUserMapper.countFollowF(wxuserId);
    }

    @Override
    public Boolean updateWxUserF(String wxAvatarUrl, String wxNickName, String wxGender, String wxCountry, String wxProvince, String wxCity, String devFans, Date wxuserCreateTime, Date updateTime, String isDelWxuser, String wxuserId) {
        return fWxUserMapper.updateWxUserF(wxAvatarUrl,wxNickName,wxGender,wxCountry,wxProvince,wxCity,devFans,wxuserCreateTime,updateTime,isDelWxuser,wxuserId);
    }

    @Override
    public Boolean updateIsDelWxuserF(String wxuserId) {
        return fWxUserMapper.updateIsDelWxuserF(wxuserId);
    }

    @Override
    public Boolean updateIsDelWxuser0F(String wxuserId) {
        return fWxUserMapper.updateIsDelWxuser0F(wxuserId);
    }

    @Override
    public List<FBaseWxuserTypeLabel> listTemplateF() {
        return fWxUserMapper.listTemplateF();
    }

    @Override
    public Boolean insertSysInformationF(String wxuserId, String sysInformationContent, String sysInformationCreateTime) {
        return fWxUserMapper.insertSysInformationF(wxuserId,sysInformationContent,sysInformationCreateTime);
    }
}
