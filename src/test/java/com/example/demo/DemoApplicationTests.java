package com.example.demo;

import com.example.demo.model.entity.UserEntity;
import com.example.demo.util.RedisUtils;

import cn.hutool.core.map.MapUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import javax.annotation.Resource;

// @ActiveProfiles("dev")
@SpringBootTest
class DemoApplicationTests {
    @Resource
    private RedisUtils redisUtils;

    @Test
    void contextLoads() {
        redisUtils.set("test", new UserEntity().setId(1).setUsername("哈哈").setEmail("giao"));

        UserEntity test = redisUtils.get("test", UserEntity.class);
        System.out.println("test = " + test);

        Map<String, String> empty = MapUtil.newHashMap();
        empty.put("qqq", "www");
        redisUtils.set("test2", empty);
        Map<String, String> test2 = redisUtils.get("test2",  Map.class);
        System.out.println("test2 = " + test2);
    }
}
