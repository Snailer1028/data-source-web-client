package com.example.demo.mapper;

import com.example.demo.mapper.query.BaseMapperX;
import com.example.demo.model.entity.common.PageResult;
import com.example.demo.model.entity.UserEntity;
import com.example.demo.model.req.UserVO;
import com.example.demo.mapper.query.LambdaQueryWrapperX;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapperX<UserEntity> {
    default PageResult<UserEntity> selectPage(UserVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserEntity>().orderByDesc(UserEntity::getId));
    }

    @Select("select * from t_user")
    List<UserEntity> selectTEST();
}




