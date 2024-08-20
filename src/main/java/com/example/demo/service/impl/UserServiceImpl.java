package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;

import org.springframework.stereotype.Service;

/**
* @author Brian
* @description 针对表【t__test_user】的数据库操作Service实现
* @createDate 2024-08-19 15:19:45
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}




