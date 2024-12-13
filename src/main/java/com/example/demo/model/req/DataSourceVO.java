package com.example.demo.model.req;

import com.example.demo.model.entity.common.PageParam;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DataSourceVO extends PageParam {
    /** 数据源主键id */
    private Long id;

    /** 数据源名称 */
    private String connName;

    /** 数据源类型 */
    private Integer type;

    // 以下的数据库连接信息可以抽象出去，不应该和业务混合
    private String username;
    private String password;

    private String host;
    private String port;
    private String database;

    private String connParam;
}
