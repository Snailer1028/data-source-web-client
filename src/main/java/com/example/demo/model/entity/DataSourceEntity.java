package com.example.demo.model.entity;

import com.example.demo.model.entity.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DataSourceEntity extends BaseEntity {
    /** 数据源主键id */
    private Long id;

    /** 数据源名称 */
    private String connName;

    /** 数据源类型 */
    private Integer type;

    // 以下的数据库连接信息可以抽象出去，不应该和业务混合
    private String username;
    private String password;
    private String url;
    private String connParam;
}
