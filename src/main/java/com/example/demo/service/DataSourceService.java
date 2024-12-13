package com.example.demo.service;

import com.example.demo.model.entity.DataSourceEntity;
import com.example.demo.model.req.DataSourceVO;

import java.util.List;

public interface DataSourceService {
    void addDataSource(DataSourceVO dataSource);
    void updateDataSource(DataSourceVO dataSource);
    void deleteDataSource(List<Integer> ids);
    List<DataSourceEntity> getDataSourceList();
    DataSourceEntity getDataSourceById(Integer dataSourceId);
}
