package com.han.gulimall.search;


import com.han.gulimall.search.config.GulimallElasticSearchConfig;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallSearchApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    public void contextLoads() throws IOException {
//        System.out.println(client);

        IndexRequest indexRequest = new IndexRequest("users");
        indexRequest.id("1"); // 设置数据的ID

        User user = new User();
        user.setAge(11);
        user.setName("zhangsan");
//        System.out.println(JSON.toJSONString(user));
        String jsonString = JSON.toJSONString(user);

        indexRequest.source(jsonString, XContentType.JSON); // 要保存的数据

        // 执行操作
        IndexResponse index = client.index(indexRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);

        System.out.println(index);
    }

}


@Data
class User {
    private int age;
    private String name;
}

