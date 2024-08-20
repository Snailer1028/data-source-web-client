package com.example.demo.controller;

import static com.example.demo.util.ResponseUtils.success;

import com.example.demo.entity.PageResult;
import com.example.demo.entity.R;
import com.example.demo.entity.User;
import com.example.demo.entity.UserVO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.util.BeanUtils;
import com.example.demo.validator.Save;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    /**
     * 新增
     *
     * @param userVO VO
     * @return R
     */
    @PostMapping
    public R<Void> saveUser(@RequestBody @Validated(Save.class) UserVO userVO) {
        userService.save(BeanUtils.toBean(userVO, User.class));
        return success();
    }

    /**
     * 详情
     *
     * @param userVO VO
     * @return R<UserVO>
     */
    @GetMapping
    public R<UserVO> getUser(@RequestBody @Validated UserVO userVO) {
        User target = userService.getById(userVO.getId());
        return success(BeanUtils.toBean(target, UserVO.class));
    }

    /**
     * 条件分页
     *
     * @param userVO VO
     * @return 分页结果
     */
    @GetMapping("/page")
    public R<PageResult<UserVO>> pageUser(@RequestBody UserVO userVO) {
        PageResult<User> userPageResult = userMapper.selectPage(userVO);
        return success(BeanUtils.toBean(userPageResult, UserVO.class));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return 分页结果
     */
    @DeleteMapping
    public R<Void> delUser(@RequestBody List<Integer> ids) {
        int i = userMapper.deleteBatchIds(ids);
        System.out.println("成功删除 " + i + " 条");
        return success();
    }
}
