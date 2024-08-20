package com.example.demo.controller;

import static com.example.demo.util.ResponseUtils.success;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.PageResult;
import com.example.demo.entity.R;
import com.example.demo.entity.User;
import com.example.demo.entity.UserVO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.util.BeanUtils;
import com.example.demo.util.MyBatisUtils;
import com.example.demo.validator.Save;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * class
 *
 * @author yanxingtong
 * @since 2024/8/16
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @PostMapping
    public R<Void> saveUser(@RequestBody @Validated(Save.class) UserVO userVO) {
        userService.save(BeanUtils.toBean(userVO, User.class));
        return success();
    }

    @GetMapping
    public R<UserVO> getUser(@RequestBody @Validated UserVO userVO) {
        User target = userService.getById(userVO.getId());
        return success(BeanUtils.toBean(target, UserVO.class));
    }

    @GetMapping("/page")
    public R<PageResult<UserVO>> pageUser(@RequestBody @Validated UserVO userVO) {
        IPage<User> page = userService.page(MyBatisUtils.buildPage(userVO));
        return success(BeanUtils.toBean(new PageResult<>(page.getRecords(), page.getTotal()), UserVO.class));
    }
    // todo 验证这个接口，按列排序
    @GetMapping("/page")
    public R<PageResult<UserVO>> pageUser2(@RequestBody @Validated UserVO userVO) {
        PageResult<User> userPageResult = userMapper.selectPage(userVO);
        return success(BeanUtils.toBean(userPageResult, UserVO.class));
    }
}
