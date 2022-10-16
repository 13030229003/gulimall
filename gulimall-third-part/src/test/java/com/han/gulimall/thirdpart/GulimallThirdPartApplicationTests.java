package com.han.gulimall.thirdpart;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallThirdPartApplicationTests {

    @Resource
    OSS ossClient;


    @Test
    public void contextLoads() throws IOException {

        FileInputStream fileInputStream = new FileInputStream("F:\\java_workspace\\123.jpg");

        String key = UUID.randomUUID().toString() + ".jpg";


        ossClient.putObject("xushuhan-mall",key,fileInputStream);

        ossClient.shutdown();
        fileInputStream.close();

        System.out.println("上传完成.....");

    }

}
