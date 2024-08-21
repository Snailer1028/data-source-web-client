package com.example.demo.controller;

import static com.example.demo.util.ResponseUtils.error;
import static com.example.demo.util.ResponseUtils.success;

import com.example.demo.entity.PageResult;
import com.example.demo.entity.R;
import com.example.demo.entity.User;
import com.example.demo.entity.UserVO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.BeanUtils;
import com.example.demo.validator.Save;
import com.example.demo.validator.Update;
import com.example.demo.validator.ValidArrayList;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * class
 *
 * @author yanxingtong
 * @since 2024/8/16
 */
@Validated
@Transactional
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserMapper userMapper;

    /**
     * 详情
     *
     * @param id id
     * @return R<UserVO>
     */
    @GetMapping("{id}")
    public R<UserVO> getUserById(@PathVariable @NotNull Integer id) {
        User target = userMapper.selectById(id);
        return success(BeanUtils.toBean(target, UserVO.class));
    }

    /**
     * 条件分页
     *
     * @param userVO VO
     * @return 分页结果
     */
    @GetMapping("/page")
    public R<PageResult<UserVO>> pageUser(@RequestBody @Validated UserVO userVO) {
        PageResult<User> userPageResult = userMapper.selectPage(userVO);
        return success(BeanUtils.toBean(userPageResult, UserVO.class));
    }

    /**
     * 删除（只是逻辑删除）
     *
     * @param ids ids
     * @return 分页结果
     */
    @DeleteMapping
    public R<Void> delUser(@RequestBody @NotEmpty List<Integer> ids) {
        int i = userMapper.deleteBatchIds(ids);
        System.out.println("成功删除 " + i + " 条");
        return success();
    }

    /**
     * 批量更新
     * 使用 @Validated + @Valid 校验集合对象
     *
     * @param list 入参
     * @return 结果
     */
    @PutMapping
    public R<Void> updateUser(@RequestBody @Validated(Update.class) ValidArrayList<UserVO> list) {
        Boolean b = userMapper.updateBatch(BeanUtils.toBean(list, User.class));
        return b ? success() : error("批量更新失败");
    }

    /**
     * 批量更新
     * 使用 @Validated + @Valid 校验集合对象
     *
     * @param list 入参
     * @return 结果
     */
    @PostMapping
    public R<Void> saveBatchUser(@RequestBody @Validated(Save.class) ValidArrayList<UserVO> list) {
        Boolean b = userMapper.insertBatch(BeanUtils.toBean(list, User.class));
        return b ? success() : error("批量新增失败");
    }
}
