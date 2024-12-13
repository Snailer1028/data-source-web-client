package com.example.demo.mapper;


import com.example.demo.mapper.query.BaseMapperX;
import com.example.demo.mapper.query.LambdaQueryWrapperX;
import com.example.demo.model.entity.DataSourceEntity;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataSourceMapper extends BaseMapperX<DataSourceEntity> {

    default int updateDataSource(DataSourceEntity entity) {
        return update(new LambdaQueryWrapperX<DataSourceEntity>()
                .eqIfPresent(DataSourceEntity::getConnName, entity.getConnName())
                .eqIfPresent(DataSourceEntity::getUrl, entity.getUrl())
                .eqIfPresent(DataSourceEntity::getUsername, entity.getUsername())
                .eqIfPresent(DataSourceEntity::getPassword, entity.getPassword())
        );
    }
}




