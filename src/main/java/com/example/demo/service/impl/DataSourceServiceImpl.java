package com.example.demo.service.impl;

import com.example.demo.enums.DBTypeEnum;
import com.example.demo.mapper.DataSourceMapper;
import com.example.demo.model.entity.DataSourceEntity;
import com.example.demo.model.req.DataSourceVO;
import com.example.demo.service.DataSourceService;
import com.example.demo.util.BeanUtils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

@Service
public class DataSourceServiceImpl implements DataSourceService {
    @Resource
    private DataSourceMapper dataSourceMapper;


    @Override
    public void addDataSource(DataSourceVO vo) {
        DataSourceEntity bean = BeanUtils.toBean(vo, DataSourceEntity.class);
        bean.setUrl(this.toJDBCUrl(vo));
        dataSourceMapper.insert(bean);
    }

    @SuppressWarnings("unchecked")
    private String toJDBCUrl(DataSourceVO vo) {
        DBTypeEnum type = DBTypeEnum.getEnumByCode(vo.getType());
        StringBuilder url = new StringBuilder(type.getJdbcPrefix() + vo.getHost() + ":" + vo.getPort());
        if (StrUtil.isNotBlank(vo.getDatabase())) {
            url.append("/").append(vo.getDatabase());
            String connParam = vo.getConnParam();
            if (StrUtil.isNotBlank(connParam)) {
                url.append(type.getDatabaseSuffix());
                Map<String, String> map = JSONUtil.toBean(connParam, Map.class);
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    url.append(entry.getKey()).append("=").append(entry.getValue()).append(type.getDelimiter());
                }
                url = new StringBuilder(StrUtil.subBefore(url, type.getDelimiter(), true));
            }
        }
        return url.toString();
    }

    @Override
    public void updateDataSource(DataSourceVO vo) {
        DataSourceEntity bean = BeanUtils.toBean(vo, DataSourceEntity.class);
        bean.setUrl(this.toJDBCUrl(vo));
        dataSourceMapper.updateDataSource(bean);
    }

    @Override
    public void deleteDataSource(List<Integer> ids) {
        dataSourceMapper.deleteBatchIds(ids);
    }

    @Override
    public List<DataSourceEntity> getDataSourceList() {
        return dataSourceMapper.selectList();
    }

    @Override
    public DataSourceEntity getDataSourceById(Integer dataSourceId) {
        return dataSourceMapper.selectById(dataSourceId);
    }
}




