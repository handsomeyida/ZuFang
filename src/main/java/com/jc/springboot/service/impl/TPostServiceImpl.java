package com.jc.springboot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jc.springboot.dao.TPostMapper;
import com.jc.springboot.dao.TPostTypeMapper;
import com.jc.springboot.entity.TAdvertBanner;
import com.jc.springboot.entity.TPostLabel;
import com.jc.springboot.service.TPostService;
import com.jc.springboot.util.ErrorEnum;
import com.jc.springboot.util.FileUtils;
import com.jc.springboot.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/23 14:34
 * @Description:
 */
@Slf4j
@Service
public class TPostServiceImpl implements TPostService {

    @Resource
    TPostMapper postMapper;
    @Resource
    TPostTypeMapper postTypeMapper;
    //获取所有帖子类型
    @Override
    public JSONObject listType(JSONObject jsonObject) {
        LoginUtil.fillPageParam(jsonObject);
        JSONObject info = new JSONObject();
        List<JSONObject> listType = postMapper.listType(jsonObject);
        int count = postMapper.countType(jsonObject);
        info.put("list", listType);
        return LoginUtil.successPage(jsonObject, listType, count);
    }

    @Override
    public JSONObject deleteType(JSONObject jsonObject) {
        postTypeMapper.deleteType(jsonObject);
        return LoginUtil.successJson();
    }

    //获取家用设备信息
    @Override
    public JSONObject listhomelabels(JSONObject jsonObject) {
        LoginUtil.fillPageParam(jsonObject);
        JSONObject info = new JSONObject();
        List<JSONObject> listType = postMapper.listhomelabels(jsonObject);
        int count = postMapper.counthomelabels(jsonObject);
        info.put("list", listType);
        return LoginUtil.successPage(jsonObject, listType, count);
    }
    //添加家用设备信息
    @Override
    public JSONObject inserthomelabels(MultipartFile[] file, TPostLabel tPostLabel) {
        //1定义要上传文件 的存放路径
        String localPath="D:/0Study/IntelliJ IDEA 2019.1.3/MyIdea/springboot/src/main/resources/static/";
        //2获得文件名字
        for (MultipartFile multipartFile : file) {
            String fileName = multipartFile.getOriginalFilename();
            //2上传失败提示
            String warning="";
            if(FileUtils.upload(multipartFile, localPath, fileName)){
                //上传成功
                String IMG_URL = "api/image/"+fileName;
                TPostLabel postLabel = new TPostLabel(1,tPostLabel.getContent(),0,IMG_URL);
                postMapper.inserthomelabels(postLabel);
            }else{
                //上传失败提示
                return LoginUtil.errorJson(ErrorEnum.E_10015);
            }
        }
        return LoginUtil.successJson();
    }
    //修改家用设备信息
    @Override
    public JSONObject updatehomelabels(MultipartFile file,TPostLabel tPostLabel) {
        if (file != null) {
            //1定义要上传文件 的存放路径
            String localPath = "D:/0Study/IntelliJ IDEA 2019.1.3/MyIdea/springboot/src/main/resources/static/";
            //2获得文件名字
            String fileName = file.getOriginalFilename();
            if (FileUtils.upload(file, localPath, fileName)) {
                //上传成功
                String IMG_URL = "api/image/" + fileName;
                TPostLabel postLabel = new TPostLabel(tPostLabel.getId(),tPostLabel.getContent(),IMG_URL);
                postMapper.updatehomelabels(postLabel);
            } else {
                //上传失败
                return LoginUtil.errorJson(ErrorEnum.E_10015);
            }
        } else {
            TPostLabel postLabel = new TPostLabel(tPostLabel.getId(),tPostLabel.getContent(),tPostLabel.getType_img_url());
            postMapper.updatehomelabels(postLabel);
        }
        return LoginUtil.successJson();
    }
    //删除家用设备信息
    @Override
    public JSONObject deletehomelabels(JSONObject jsonObject) {
        postMapper.deletehomelabels(jsonObject);
        return LoginUtil.successJson();
    }
    //获取热门商圈信息
    @Override
    public JSONObject listshoplabels() {
        List<JSONObject> listType = postMapper.listshoplabels();
        return LoginUtil.successPage(listType);
    }
    //添加热门商圈信息
    @Override
    public JSONObject insertshoplabels(JSONObject jsonObject) {
        String parent_id = jsonObject.getString("parentId");
        int i = 0;
        if (parent_id == null || parent_id.length() == 0){
            return LoginUtil.errorJson(ErrorEnum.E_10013 );
        } else {
            i = postMapper.loadExistlabels(jsonObject);
        }
        if (i > 0) {
            String content = jsonObject.getString("content");
            if (content == null || content.length() == 0){
                return LoginUtil.errorJson(ErrorEnum.E_10011 );
            } else {
                postMapper.insertshoplabels(jsonObject);
            }
        } else {
            TPostLabel tPostLabel = new TPostLabel(2,parent_id);
            postMapper.insertparentlabels(tPostLabel);
            jsonObject.put("parentId", tPostLabel.getId());
            postMapper.insertshoplabels(jsonObject);
        }
        return LoginUtil.successJson();
    }
    //修改热门商圈信息
//    @Override
//    public JSONObject updateshoplabels(JSONObject jsonObject) {
//        postMapper.updateshoplabels(jsonObject);
//        return LoginUtil.successJson();
//    }
    //删除热门商圈信息
    @Override
    public JSONObject deleteonelabels(JSONObject jsonObject) {
        postMapper.deleteshoplabels(jsonObject);
        return LoginUtil.successJson();
    }
    //删除热门商圈信息
    @Override
    public JSONObject deletealllabels(JSONObject jsonObject) {
        postMapper.deleteparentlabels(jsonObject);
        List<Integer> sonid = (List<Integer>)jsonObject.get("sonid");
        for (int i=0; i<sonid.size(); i++) {
            jsonObject.put("Sid", sonid.get(i));
            postMapper.deleteshoplabels(jsonObject);
        }
        return LoginUtil.successJson();
    }
    //获取地铁周边信息
    @Override
    public JSONObject listsubwaylabels() {
        List<JSONObject> listType = postMapper.listsubwaylabels();
        return LoginUtil.successPage(listType);
    }
    //添加地铁周边信息
    @Override
    public JSONObject insertsubwaylabels(JSONObject jsonObject) {
        String parent_id = jsonObject.getString("parentId");
        int i = 0;
        if (parent_id == null || parent_id.length() == 0){
            return LoginUtil.errorJson(ErrorEnum.E_10014 );
        } else {
            i = postMapper.loadExistlabels(jsonObject);
        }
        if (i > 0) {
            String content = jsonObject.getString("content");
            if (content == null || content.length() == 0){
                return LoginUtil.errorJson(ErrorEnum.E_10012);
            } else {
                postMapper.insertshoplabels(jsonObject);
            }
        } else {
            TPostLabel tPostLabel = new TPostLabel(3,parent_id);
            postMapper.insertparentlabels(tPostLabel);
            jsonObject.put("parentId", tPostLabel.getId());
            postMapper.insertshoplabels(jsonObject);
        }
        return LoginUtil.successJson();
    }
    //设置标签的排序号
    @Override
    public JSONObject updateIndex(JSONObject jsonObject) {
        List<Integer> listid = (List<Integer>) jsonObject.get("listID");
        List<Integer> listindex = (List<Integer>) jsonObject.get("listIndex");
        for (int i = 0; i < listid.size(); i++) {
            postMapper.updateIndex(listid.get(i), listindex.get(i));
        }
        return LoginUtil.successJson();
    }
    //标签改变顺序后的排序
    @Override
    public JSONObject updateChangeIndex(JSONObject jsonObject) {
        List<Integer> listid = (List<Integer>) jsonObject.get("listID");
        List<Integer> listindex = (List<Integer>) jsonObject.get("listIndex");
        int j = 0;
        for (int i = listid.size()-1; i >= 0 ; i--) {
            postMapper.updateIndex(listid.get(j), listindex.get(i));
            j++;
        }
        return LoginUtil.successJson("success");
    }
}
