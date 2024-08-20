package com.example.demo.mapper;

import com.example.demo.entity.PageResult;
import com.example.demo.entity.User;
import com.example.demo.entity.UserVO;
import com.example.demo.mapper.query.LambdaQueryWrapperX;

import org.apache.ibatis.annotations.Mapper;

/**
* @author Brian
* @description 针对表【t__test_user】的数据库操作Mapper
* @createDate 2024-08-19 15:19:45
* @Entity generator.domain.T_testUser
*/
@Mapper
public interface UserMapper extends BaseMapperX<User> {
    default PageResult<User> selectPage(UserVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<User>()
                .likeIfPresent(User::getName, reqVO.getName())
                .orderByDesc(User::getId));
    }
}




