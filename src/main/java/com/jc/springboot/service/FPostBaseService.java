package com.jc.springboot.service;

import com.jc.springboot.entity.FBaseWxuserTypeLabel;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FPostBaseService {

    List<FBaseWxuserTypeLabel> listPostF(String page
                                        ,String limit
                                        ,String baseCreateTime
                                        ,String typeName
                                        ,String labels
                                        ,String wxNickName);
    int countPostF(String baseCreateTime,String typeName,String labels,String wxNickName);

    //帖子表逻辑删除
    Boolean updatePostIsDelF(String postId);
    //帖子表逻辑恢复
    Boolean updatePostIsDelR(String postId);
    //帖子表逻辑上架
    Boolean updateIsLowerShelfU(String postId);
    //帖子表逻辑下架
    Boolean updateIsLowerShelfD(String postId);

    //获取所有导航表的信息
    List<FBaseWxuserTypeLabel> listNavF();
    //获取当前行postId，然后根据postId查询出置顶表对应的多个导航栏Id并查出导航栏名字
    List<FBaseWxuserTypeLabel> getListNavName(String postId);
    //查询t_navigation_top表对应postId和navigationTopId的数据是否存在
    FBaseWxuserTypeLabel listNavigationTop(String postId, String navigationId);
    //t_navigation_top表的添加
    Boolean insertTopF(String postId, String navigationTopId, String sortTime, String topCreateTime);
    //帖子表置顶
    Boolean updateTopIsDel0(String postId, List<String> listCheckNavName, String sortTime);
    //帖子表逻辑取消置顶
    Boolean updateTopIsDel1(String postId, List<String> listCheckNavName1);

    //获取所有帖子类型
    List<FBaseWxuserTypeLabel> listPostTypeF();

    //获取所有标签表信息
    List<FBaseWxuserTypeLabel> listPostLabelF();
    //获取当前行postId，然后根据postId查询出置顶表对应的多个导航栏Id并查出导航栏名字
    List<FBaseWxuserTypeLabel> getListLabelName(String PostId);
    //修改帖子
    Boolean updatePostF(String postId,            String content,
                        String minPrice,          String maxPrice,
                        String phone,             String address,
                        String totalBrowse,       String totalResonate,
                        String devBrowse,         String devResonate,
                        Date activeTime,          String totalCollection,
                        String totalComments,     String fee,
                        String isSub,             String isMall,
                        String id);
    //t_post_base_label表的逻辑删除
    Boolean updateBaseLabelIsDel1(String postId, List<String> listCheckLabelName1);
    //t_post_base_label表的删除
    Boolean deleteBaseLabelF(String postId);
    //t_post_base_label表的添加
    Boolean insertBaseLabelF(String postId, String postLabelId);
}
