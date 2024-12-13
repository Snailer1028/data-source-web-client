package com.example.demo.controller;


import com.example.demo.datasource.DynamicDataSource;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.entity.UserEntity;
import com.example.demo.model.entity.DataSourceEntity;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.annotation.Resource;

@RestController
@RequestMapping("/datasource")
public class DatasourceController {
    @Resource
    private UserMapper userMapper;

    @Autowired
    private DynamicDataSource dynamicDataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 添加新数据源
    @PostMapping("/add")
    public String addDataSource(@RequestBody DataSourceEntity dataSource) {
        boolean success = dynamicDataSource.addDataSource(dataSource.getConnName(), dataSource);
        return success ? "数据源添加成功" : "数据源添加失败";
    }

    // 切换数据源
    @PostMapping("/switch")
    public String switchDataSource(@RequestParam String name) {
        DynamicDataSource.setDataSourceKey(name);
        return "已切换到数据源：" + name;
    }

    @GetMapping("test")
    public List<UserEntity> test(@RequestParam String key){
        DynamicDataSource.setDataSourceKey(key);
        List<UserEntity> userEntities = userMapper.selectTEST();
        DynamicDataSource.removeDataSourceKey();
        return userEntities;
    }
}

