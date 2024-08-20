package com.example.demo.util;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.PageParam;
import com.example.demo.entity.SortingField;

import cn.hutool.core.collection.CollectionUtil;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MyBatis 工具类
 */
public class MyBatisUtils {

    public static <T> Page<T> buildPage(PageParam pageParam) {
        return buildPage(pageParam, null);
    }

    public static <T> Page<T> buildPage(PageParam pageParam, Collection<SortingField> sortingFields) {
        // 页码 + 数量
        Page<T> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());

        // 排序字段
        if (CollectionUtil.isNotEmpty(sortingFields)) {
            List<OrderItem> collect = sortingFields.stream().map(sortingField ->
                            SortingField.ORDER_ASC.equals(sortingField.getOrder()) ?
                            OrderItem.asc(sortingField.getField()) : OrderItem.desc(sortingField.getField()))
                    .collect(Collectors.toList());
            page.addOrder(collect);
        }
        return page;
    }
}
