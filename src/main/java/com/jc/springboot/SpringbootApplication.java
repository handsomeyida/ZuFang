package com.jc.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@MapperScan("com.jc.springboot.dao")
@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication application=new SpringApplication(SpringbootApplication.class);
        /**
         * OFF G关闭
         * CLOSED 后台控制台输出，默认就是这种
         * LOG 日志输出
         */
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
//        SpringApplication.run(SpringbootApplication.class, args);
    }

}
