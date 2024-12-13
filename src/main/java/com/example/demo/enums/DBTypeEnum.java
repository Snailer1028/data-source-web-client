package com.example.demo.enums;

import com.example.demo.exception.ServiceException;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum DBTypeEnum {
    MYSQL(0, "mysql", "com.mysql.cj.jdbc.Driver", "jdbc:mysql://", "&", "?"),
    POSTGRESQL(1, "postgresql", "org.postgresql.Driver", "jdbc:postgresql://", "&", "?");

    private final int code;
    private final String desc;
    private final String driver;
    private final String jdbcPrefix;
    private final String delimiter;
    private final String databaseSuffix;

    /**
     * 根据数字找类型
     *
     * @param dbType db类型
     * @return DB枚举
     */
    public static DBTypeEnum getEnumByCode(Integer dbType) {
        Optional<DBTypeEnum> first = Arrays.stream(DBTypeEnum.values()).filter(val -> val.getCode() == dbType).findFirst();
        return first.orElseThrow(ServiceException::new);
    }
}
