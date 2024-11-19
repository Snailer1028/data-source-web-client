package com.example.demo.mapper;

import com.example.demo.entity.PageResult;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.UserVO;
import com.example.demo.mapper.query.LambdaQueryWrapperX;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapperX<UserEntity> {
    default PageResult<UserEntity> selectPage(UserVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserEntity>().orderByDesc(UserEntity::getId));
    }
}




