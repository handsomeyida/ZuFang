package com.jc.springboot.service.impl;


import com.jc.springboot.dao.FPostBaseMapper;
import com.jc.springboot.entity.FBaseWxuserTypeLabel;
import com.jc.springboot.entity.FPageRange;
import com.jc.springboot.service.FPostBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class FPostBaseServiceImpl implements FPostBaseService {

    @Resource
    FPostBaseMapper fPostBaseMapper;

    @Override
    public List<FBaseWxuserTypeLabel> listPostF(String page, String limit, String baseCreateTime, String typeName, String labels, String wxNickName) {
        FPageRange pageRange = new FPageRange(page,limit);
        String listLabelName = "";
        List<String> postIds = fPostBaseMapper.listPostIdF();
        for (String postId : postIds) {
            List<String> postLabelIds = fPostBaseMapper.listPostLabelsF(postId);
            for (String postLabelId : postLabelIds) {
                String labelName = fPostBaseMapper.getLabelNameF(postLabelId);
                listLabelName += labelName+",";
            }
            fPostBaseMapper.updatePostLabelsF(listLabelName,postId);
            listLabelName = "";
        }
        return fPostBaseMapper.listPostF(pageRange.getStart(),pageRange.getEnd(),baseCreateTime,typeName,labels,wxNickName);
}

    @Override
    public int countPostF(String baseCreateTime, String typeName, String labels, String wxNickName) {
        return fPostBaseMapper.countPostF(baseCreateTime,typeName,labels,wxNickName);
    }

    @Override
    public Boolean updatePostIsDelF(String postId) {
        return fPostBaseMapper.updatePostIsDelF(postId);
    }

    @Override
    public Boolean updatePostIsDelR(String postId) {
        return fPostBaseMapper.updatePostIsDelR(postId);
    }

    @Override
    public Boolean updateIsLowerShelfU(String postId) {
        return fPostBaseMapper.updateIsLowerShelfU(postId);
    }

    @Override
    public Boolean updateIsLowerShelfD(String postId) {
        return fPostBaseMapper.updateIsLowerShelfD(postId);
    }

    @Override
    public Boolean updateTopIsDel0(String postId, List<String> listCheckNavName, String sortTime) {
        return fPostBaseMapper.updateTopIsDel0(postId,listCheckNavName,sortTime);
    }

    @Override
    public List<FBaseWxuserTypeLabel> listNavF() {
        return fPostBaseMapper.listNavF();
    }

    @Override
    public List<FBaseWxuserTypeLabel> getListNavName(String postId) {
        return fPostBaseMapper.getListNavName(postId);
    }

    @Override
    public FBaseWxuserTypeLabel listNavigationTop(String postId, String navigationId) {
        return fPostBaseMapper.listNavigationTop(postId,navigationId);
    }

    @Override
    public Boolean insertTopF(String postId, String navigationTopId, String sortTime, String topCreateTime) {
        return fPostBaseMapper.insertTopF(postId,navigationTopId,sortTime,topCreateTime);
    }

    @Override
    public Boolean updateTopIsDel1(String postId, List<String> listCheckNavName1) {
        return fPostBaseMapper.updateTopIsDel1(postId,listCheckNavName1);
    }

    @Override
    public List<FBaseWxuserTypeLabel> listPostTypeF() {
        return fPostBaseMapper.listPostTypeF();
    }

    @Override
    public List<FBaseWxuserTypeLabel> listPostLabelF() {
        return fPostBaseMapper.listPostLabelF();
    }

    @Override
    public List<FBaseWxuserTypeLabel> getListLabelName(String PostId) {
        return fPostBaseMapper.getListLabelName(PostId);
    }
    @Override
    public Boolean updatePostF(String postId,            String content,
                               String minPrice,          String maxPrice,
                               String phone,             String address,
                               String totalBrowse,       String totalResonate,
                               String devBrowse,         String devResonate,
                               Date activeTime,          String totalCollection,
                               String totalComments,     String fee,
                               String isSub,             String isMall,
                               String id) {
        return fPostBaseMapper.updatePostF(postId,content,minPrice,maxPrice,
                                           phone,address,totalBrowse,totalResonate,
                                           devBrowse,devResonate,activeTime,
                                           totalCollection,totalComments,
                                           fee,isSub,isMall,id);
    }
    @Override
    public Boolean updateBaseLabelIsDel1(String postId, List<String> listCheckLabelName1) {
        return fPostBaseMapper.updateBaseLabelIsDel1(postId,listCheckLabelName1);
    }

    @Override
    public Boolean deleteBaseLabelF(String postId) {
        return fPostBaseMapper.deleteBaseLabelF(postId);
    }

    @Override
    public Boolean insertBaseLabelF(String postId, String postLabelId) {
        return fPostBaseMapper.insertBaseLabelF(postId,postLabelId);
    }
}
