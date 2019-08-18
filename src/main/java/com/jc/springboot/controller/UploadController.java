package com.jc.springboot.controller;

import com.jc.springboot.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
    public String imgUpload(@RequestParam("file") MultipartFile[] file, String AdvertTitle){

        //1定义要上传文件 的存放路径
        String localPath="D:/1MyFile/图片/材料图片/项目图片";
        //2获得文件名字
        for (MultipartFile multipartFile : file) {
            String fileName = multipartFile.getOriginalFilename();
            //2上传失败提示
            String warning="";
            if(FileUtils.upload(multipartFile, localPath, fileName)){
                //上传成功
                warning="上传成功";

            }else{
                warning="上传失败";
            }
            System.out.println(warning);
        }
        return "上传成功";
    }
    /**
     * 显示图片
     * @param fileName 文件名
     * @return
     */

    @RequestMapping("show")
    public ResponseEntity show(String fileName){
        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + path + fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
