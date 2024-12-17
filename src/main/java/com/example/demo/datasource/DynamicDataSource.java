package com.example.demo.datasource;

import com.example.demo.enums.DBTypeEnum;
import com.example.demo.model.entity.DataSourceEntity;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class DynamicDataSource extends AbstractRoutingDataSource {
    // 存放数据源的 Map
    private static final Map<Object, Object> DATA_SOURCE_MAP = new ConcurrentHashMap<>();

    // 当前线程数据源 key
    private static final ThreadLocal<String> DATA_SOURCE_KEY = new ThreadLocal<>();

    public DynamicDataSource() {
        super.setTargetDataSources(DATA_SOURCE_MAP);
    }


    /**
     * 设置当前线程的数据源 order 1
     *
     * @param key key
     */
    public static void setDataSourceKey(String key) {
        DATA_SOURCE_KEY.set(key);

    }

    /**
     * 每次执行sql都会从ThreadLocal获取Key, AbstractRoutingDataSource会根据key获取对应的数据源并准备好连接池
     * order 2
     *
     * @return key(String)
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DATA_SOURCE_KEY.get();
    }

    /**
     * 移除threadLocal order 3
     */
    public static void removeDataSourceKey() {
        DATA_SOURCE_KEY.remove();
    }


    // 添加新数据源
    public boolean addDataSource(String key, DataSourceEntity config) {
        try {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(config.getUrl());
            dataSource.setUsername(config.getUsername());
            dataSource.setPassword(config.getPassword());
            dataSource.setDriverClassName(DBTypeEnum.getEnumByCode(config.getType()).getDriver());
            // 将新数据源添加到 Map 中
            DATA_SOURCE_MAP.put(key, dataSource);
            // 重新设置目标数据源，让 Spring 重新加载数据源
            super.setTargetDataSources(DATA_SOURCE_MAP);
            super.afterPropertiesSet();
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public void delDataSource(String connName) {
        HikariDataSource removedDataSource = (HikariDataSource)DATA_SOURCE_MAP.remove(connName);
        removedDataSource.close();
        // 重新设置目标数据源，让 Spring 重新加载数据源
        super.setTargetDataSources(DATA_SOURCE_MAP);
        super.afterPropertiesSet();
    }


    public void updateDataSource(String key, DataSourceEntity config) {
        HikariDataSource hikariDataSource = (HikariDataSource)DATA_SOURCE_MAP.get(key);
        hikariDataSource.setJdbcUrl(config.getUrl());
        hikariDataSource.setUsername(config.getUsername());
        hikariDataSource.setPassword(config.getPassword());
        hikariDataSource.setDriverClassName(DBTypeEnum.getEnumByCode(config.getType()).getDriver());

        // 重新设置目标数据源，让 Spring 重新加载数据源
        super.setTargetDataSources(DATA_SOURCE_MAP);
        super.afterPropertiesSet();
    }
}
