package com.jc.springboot.controller;

import com.jc.springboot.dao.TAdvertBannerMapper;
import com.jc.springboot.entity.TAdvertBanner;
import com.jc.springboot.util.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/18 17:11
 * @Description:
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

    //文件上传接口
    @RequiresPermissions("upload:imgupload")
    @RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
    public String imgUpload(@RequestParam("file") MultipartFile[] file, String AdvertTitle){

        //1定义要上传文件 的存放路径
        String localPath="D:/1MyFile/image";
        //2获得文件名字
        for (MultipartFile multipartFile : file) {
            String fileName = multipartFile.getOriginalFilename();
            //2上传失败提示
            String warning="";
            if(FileUtils.upload(multipartFile, localPath, fileName)){
                //上传成功
                String IMG_URL = localPath+"/"+fileName;
                Date date = new Date();
                TAdvertBanner tAdvertBanner = new TAdvertBanner(AdvertTitle,IMG_URL,date,date,0);
                advertBannerMapper.insert(tAdvertBanner);
                warning="上传成功";
            }else{
                warning="上传失败";
            }
            System.out.println(warning);
        }
        return "上传成功";
    }
}
