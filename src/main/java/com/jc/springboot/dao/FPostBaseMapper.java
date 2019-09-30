package com.jc.springboot.dao;

import com.jc.springboot.entity.FBaseWxuserTypeLabel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface FPostBaseMapper {
    //获取所有帖子的Id
    List<String> listPostIdF();
    //根据帖子Id查找中间关系表的POST_LABEL_ID
    List<String> listPostLabelsF(@Param("postId") String postId);
    //查询对应labelId的标签名称
    String getLabelNameF(@Param("labelId") String labelId);
    //将标签名称设置到帖子表里的labels字段
    Boolean updatePostLabelsF(@Param("listLabelName") String listLabelName,@Param("postId") String postId);
    //查询所有帖子信息
    List<FBaseWxuserTypeLabel> listPostF(
            @Param("start") int start, @Param("end") int end
            ,@Param("baseCreateTime") String baseCreateTime
            ,@Param("typeName") String typeName
            ,@Param("labels") String labels
            ,@Param("wxNickName") String wxNickName
            );
    //统计帖子表数据总条数(包含模糊查询后的总条数)
    int countPostF(String baseCreateTime,String typeName,String labels,String wxNickName);

    //帖子表逻辑删除
    Boolean updatePostIsDelF(@Param("postId") String postId);
    //帖子表逻辑恢复
    Boolean updatePostIsDelR(@Param("postId") String postId);
    //帖子表逻辑上架
    Boolean updateIsLowerShelfU(@Param("postId") String postId);
    //帖子表逻辑下架
    Boolean updateIsLowerShelfD(@Param("postId") String postId);

    //查询t_navigation_top表对应postId和navigationTopId的数据是否存在
    FBaseWxuserTypeLabel listNavigationTop(@Param("postId") String postId, @Param("navigationId") String navigationId);
    //t_navigation_top表的添加
    Boolean insertTopF(@Param("postId") String postId, @Param("navigationTopId") String navigationTopId, @Param("sortTime") String sortTime, @Param("topCreateTime") String topCreateTime);
    //帖子表置顶
    Boolean updateTopIsDel0(@Param("postId") String postId, @Param("listCheckNavName") List<String> listCheckNavName, @Param("sortTime") String sortTime);
    //帖子表逻辑取消置顶
    Boolean updateTopIsDel1(@Param("postId") String postId, @Param("listCheckNavName1") List<String> listCheckNavName1);
    //获取所有导航表的信息
    List<FBaseWxuserTypeLabel> listNavF();
    //获取当前行postId，然后根据postId查询出置顶表对应的多个导航栏Id并查出导航栏名字
    List<FBaseWxuserTypeLabel> getListNavName (@Param("postId") String PostId);

    //获取所有帖子类型
    List<FBaseWxuserTypeLabel> listPostTypeF();

    //获取所有标签表信息
    List<FBaseWxuserTypeLabel> listPostLabelF();
    //根据当前行postId查出标签名字
    List<FBaseWxuserTypeLabel> getListLabelName(@Param("postId") String PostId);
    //修改帖子
    Boolean updatePostF(@Param("postId") String postId,
                        @Param("content") String content,                 @Param("minPrice") String minPrice,
                        @Param("maxPrice") String maxPrice,               @Param("phone") String phone,
                        @Param("address") String address,                 @Param("totalBrowse") String totalBrowse,
                        @Param("totalResonate") String totalResonate,     @Param("devBrowse") String devBrowse,
                        @Param("devResonate") String devResonate,         @Param("activeTime") Date activeTime,
                        @Param("totalCollection") String totalCollection, @Param("totalComments") String totalComments,
                        @Param("fee") String fee,                         @Param("isSub") String isSub,
                        @Param("isMall") String isMall,                   @Param("id") String id);
    //t_post_base_label表的逻辑删除
    Boolean updateBaseLabelIsDel1(@Param("postId") String postId, @Param("listCheckLabelName1") List<String> listCheckLabelName1);
    //t_post_base_label表的删除
    Boolean deleteBaseLabelF(@Param("postId") String postId);
    //t_post_base_label表的添加
    Boolean insertBaseLabelF(@Param("postBaseId") String postId, @Param("postLabelId") String postLabelId);
}
