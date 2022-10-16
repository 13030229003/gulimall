package com.han.gulimall.thirdpart.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @PACKAGE_NAME: com.han.gulimall.thirdpart.controller
 * @Author XSH
 * @Date 2022-10-14 17:55
 * @Version 1.0.0
 * @Description：
 **/
@RestController

public class TestOSSController {

    @Resource
    private OSS ossClient;

    @RequestMapping("/oss")
    public String oos() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("F:\\java_workspace\\123.jpg");

        String key = UUID.randomUUID().toString() + ".jpg";


        ossClient.putObject("xushuhan-mall",key,fileInputStream);

        ossClient.shutdown();
        fileInputStream.close();

       return "上传完成....";
    }

}
