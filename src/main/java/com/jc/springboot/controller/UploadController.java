package com.jc.springboot.controller;

import com.jc.springboot.dao.TPostTypeMapper;
import com.jc.springboot.dao.TAdvertBannerMapper;
import com.jc.springboot.entity.TPostType;
import com.jc.springboot.entity.TAdvertBanner;
import com.jc.springboot.util.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/18 17:11
 * @Description: 文件上传
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    private final ResourceLoader resourceLoader;

    @Autowired
    public UploadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${web.upload-path}")
    private String path;

    @Resource
    private TAdvertBannerMapper advertBannerMapper;

    @Resource
    private TPostTypeMapper postTypeMapper;

    //多文件上传接口
    @RequiresPermissions("upload:imgupload")
    @RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
    public String imgUpload(@RequestParam("file") MultipartFile[] file, String AdvertTitle){

        String message = "";
        //1定义要上传文件 的存放路径
        String localPath="D:/0Study/IntelliJ IDEA 2019.1.3/MyIdea/springboot/src/main/resources/static/";
        //2获得文件名字
        for (MultipartFile multipartFile : file) {
            String fileName = multipartFile.getOriginalFilename();
            //2上传失败提示
            String warning="";
            if(FileUtils.upload(multipartFile, localPath, fileName)){
                //上传成功
                String IMG_URL = "image/"+fileName;
                Date date = new Date();
                TAdvertBanner tAdvertBanner = new TAdvertBanner(AdvertTitle,IMG_URL,date,date,0);
                advertBannerMapper.insert(tAdvertBanner);
                warning="上传成功";
                message=warning;
            }else{
                warning="上传失败";
                message=warning;
            }
        }
        return message;
    }

    //单文件上传接口
    @RequiresPermissions("upload:imgupload")
    @RequestMapping(value = "/typeimgUpload", method = RequestMethod.POST)
    public String TypeimgUpload(@RequestParam("file") MultipartFile file, TPostType postType){
        String message = "";
        if (file != null) {
            //1定义要上传文件 的存放路径
            String localPath="D:/0Study/IntelliJ IDEA 2019.1.3/MyIdea/springboot/src/main/resources/static/";
            //2获得文件名字
            String fileName = file.getOriginalFilename();
            //2上传失败提示
            String warning="";
            if(FileUtils.upload(file, localPath, fileName)){
                //上传成功
                String IMG_URL = "api/image/"+fileName;
                TPostType postType2 = new TPostType(postType.getId(),IMG_URL,postType.getType_name());
                postTypeMapper.update(postType2);
                warning="上传成功";
                message=warning;
            }else{
                warning="上传失败";
                message=warning;
            }
        } else {
            TPostType postType2 = new TPostType(postType.getId(),postType.getType_name());
            postTypeMapper.update(postType2);
            message="上传成功";
        }
        return message;
    }

}
