package com.jc.springboot.controller;

import com.jc.springboot.entity.FBaseWxuserTypeLabel;
import com.jc.springboot.service.FPostBaseService;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.jc.springboot.util.model.response.IResult;
import com.jc.springboot.util.model.response.PageResultBean;
import com.jc.springboot.util.model.response.ResultBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/FPost")
public class FPostBaseController {

    @Resource
    FPostBaseService fPostBaseService;

//    @RequiresPermissions("guide:list")
    //查询所有帖子信息
    @RequestMapping("/listPostF")
    public IResult listPostF(String page, String limit, String baseCreateTime, String typeName, String labels, String wxNickName){
        return new PageResultBean<Collection<FBaseWxuserTypeLabel>>(fPostBaseService.listPostF(page,limit,baseCreateTime,typeName,labels,wxNickName),fPostBaseService.countPostF(baseCreateTime,typeName,labels,wxNickName));
    }
    //查询所有帖子类型
    @RequestMapping("/listPostTypeF")
    public IResult listPostTypeF(){
        return new ResultBean<Collection<FBaseWxuserTypeLabel>>(fPostBaseService.listPostTypeF());
    }
    //查询所有导航表信息
    @RequestMapping("/listNavF")
    public IResult listNavF(){
        return new ResultBean<Collection<FBaseWxuserTypeLabel>>(fPostBaseService.listNavF());
    }
    //帖子表逻辑删除
    @RequestMapping("/updatePostIsDelF")
    public IResult updatePostIsDelF(String postId){
        return new ResultBean<Boolean>(fPostBaseService.updatePostIsDelF(postId));
    }
    //帖子表逻辑恢复
    @RequestMapping("/updatePostIsDelR")
    public IResult updatePostIsDelR(String postId){
        return new ResultBean<Boolean>(fPostBaseService.updatePostIsDelR(postId));
    }
    //帖子表逻辑上架
    @RequestMapping("/updateIsLowerShelfU")
    public IResult updateIsLowerShelfU(String postId){
        return new ResultBean<Boolean>(fPostBaseService.updateIsLowerShelfU(postId));
    }
    //帖子表逻辑下架
    @RequestMapping("/updateIsLowerShelfD")
    public IResult updateIsLowerShelfD(String postId){
        return new ResultBean<Boolean>(fPostBaseService.updateIsLowerShelfD(postId));
    }
    //帖子表逻辑置顶
    @RequestMapping("/updateTopIsDel0")
    public IResult updateTopIsDel0(@RequestBody FBaseWxuserTypeLabel postAction){
        List<String> listCheckNavName = postAction.getListCheckNavName();
        String postId = postAction.getPostId();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sortTime = time.format(new Date());
        return new ResultBean<Boolean>(fPostBaseService.updateTopIsDel0(postId,listCheckNavName,sortTime));
    }
    //帖子表逻辑取消置顶
    @RequestMapping("/updateTopIsDel1")
    public IResult updateTopIsDel1(@RequestBody FBaseWxuserTypeLabel postAction){
        List<String> listCheckNavName1 = postAction.getListCheckNavName1();
        String postId = postAction.getPostId();
        return new ResultBean<Boolean>(fPostBaseService.updateTopIsDel1(postId,listCheckNavName1));
    }
    //获取所有postId，逐个遍历出来，然后根据postId查询出置顶表对应的多个导航栏Id
    @RequestMapping("/getListNavName")
    public IResult getListNavName(@RequestBody FBaseWxuserTypeLabel postAction){
        String postId = postAction.getPostId();
        return new ResultBean<Collection<FBaseWxuserTypeLabel>>(fPostBaseService.getListNavName(postId));
    }
    //查询所有标签表信息
    @RequestMapping("/listPostLabelF")
    public IResult listPostLabelF(){
        return new ResultBean<Collection<FBaseWxuserTypeLabel>>(fPostBaseService.listPostLabelF());
    }
    //获取所有postId，逐个遍历出来，然后根据postId查询出置顶表对应的多个导航栏Id
    @RequestMapping("/getListLabelName")
    public IResult getListLabelName(@RequestBody FBaseWxuserTypeLabel postLabel){
        String postId = postLabel.getPostId();
        return new ResultBean<Collection<FBaseWxuserTypeLabel>>(fPostBaseService.getListLabelName(postId));
    }
    //t_post_base_label表的逻辑删除
    @RequestMapping("/updateBaseLabelIsDel1")
    public IResult updateBaseLabelIsDel1(@RequestBody FBaseWxuserTypeLabel postLabel){
        List<String> listCheckLabelName1 = postLabel.getListCheckLabelName1();
        String postId = postLabel.getPostId();
        return new ResultBean<Boolean>(fPostBaseService.updateBaseLabelIsDel1(postId,listCheckLabelName1));
    }
    //t_post_base_label表的删除
    @RequestMapping("/deleteBaseLabelF")
    public IResult deleteBaseLabelF(@RequestBody FBaseWxuserTypeLabel postLabel){
        String postId = postLabel.getPostId();
        return new ResultBean<Boolean>(fPostBaseService.deleteBaseLabelF(postId));
    }
    //t_post_base_label表的添加
    @RequestMapping("/insertBaseLabelF")
    public IResult insertBaseLabelF(String postId, String postLabelId){
        return new ResultBean<Boolean>(fPostBaseService.insertBaseLabelF(postId,postLabelId));
    }
    //t_post_base表的修改
    @RequestMapping("/updatePostF")
    public IResult updatePostF(@RequestBody FBaseWxuserTypeLabel tempPost){
        String postId = tempPost.getPostId();
        String content = tempPost.getContent();
        String minPrice = tempPost.getMinPrice();
        String maxPrice = tempPost.getMaxPrice();
        String phone = tempPost.getPhone();
        String address = tempPost.getAddress();
        String totalBrowse = tempPost.getTotalBrowse();
        String totalResonate = tempPost.getTotalResonate();
        String devBrowse = tempPost.getDevBrowse();
        String devResonate = tempPost.getDevResonate();
        Date activeTime = tempPost.getActiveTime();
        String totalCollection = tempPost.getTotalCollection();
        String totalComments = tempPost.getTotalComments();
        String fee = tempPost.getFee();
        String isSub = tempPost.getIsSub();
        String isMall = tempPost.getIsMall();
        String id = tempPost.getId();
        return new ResultBean<Boolean>(fPostBaseService.updatePostF(postId,content,minPrice,maxPrice,
                                                                    phone,address,totalBrowse,totalResonate,
                                                                    devBrowse,devResonate,activeTime,totalCollection,
                                                                    totalComments,fee,isSub,isMall,id));
    }

    //查询t_navigation_top表对应postId和navigationTopId的数据是否存在
    @RequestMapping("/listNavigationTop")
    public IResult listNavigationTop(String postId, String navigationTopId){
        return new ResultBean<FBaseWxuserTypeLabel>(fPostBaseService.listNavigationTop(postId,navigationTopId));
    }
    //t_navigation_top表的添加
    @RequestMapping("/insertTopF")
    public IResult insertTopF(String postId, String navigationTopId){
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sortTime = time.format(new Date());
        String topCreateTime = time.format(new Date());
        return new ResultBean<Boolean>(fPostBaseService.insertTopF(postId, navigationTopId, sortTime, topCreateTime));
    }
}
