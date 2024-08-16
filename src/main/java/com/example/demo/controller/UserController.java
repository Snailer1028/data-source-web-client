package com.example.demo.controller;

import static com.example.demo.util.ResponseUtils.success;

import com.example.demo.entity.R;
import com.example.demo.entity.User;
import com.example.demo.validator.Save;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * class
 *
 * @author yanxingtong
 * @since 2024/8/16
 */
@RestController
public class UserController {
    @PostMapping("user")
    public R<User> getUser(@RequestBody @Validated(Save.class) User user) {
        System.out.println("user = " + user);
        return success(user);
    }
}
