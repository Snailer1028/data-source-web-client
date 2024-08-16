package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常类型枚举
 *
 * @author yanxingtong
 * @since 2023-11-16
 */
@Getter
@AllArgsConstructor
public enum ExceptionTypeEnum {
    DEFAULT_EXCEPTION_TYPE(1000, "Default Exception Type"),

    DATASOURCE_DEFAULT_TYPE(1100, "数据源模块异常"),
    DATASOURCE_VALID_TYPE(1101, "数据源模块入参校验不通过"),
    DATASOURCE_JDBC_TYPE(1102, "数据源模块中JDBC发生异常"),
    DATASOURCE_MYBATIS_TYPE(1103, "数据源模块mybatis发生异常");

    /** 业务标识码 */
    private final int id;
    private final String desc;

    @Override
    public String toString() {
        return "[" + id + "-" + desc + "]";
    }
}
