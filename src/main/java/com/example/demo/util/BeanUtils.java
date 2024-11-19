package com.example.demo.util;

import com.example.demo.model.entity.PageResult;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Bean 工具类
 * 1. 默认使用 {@link BeanUtil} 作为实现类
 * 2. 针对复杂的对象转换，可以搜参考 AuthConvert 实现，通过 mapstruct + default 配合实现
 *
 * @author yan
 */
public class BeanUtils {
    /**
     * 对象或Map转Bean
     *
     * @param <T> 转换的Bean类型
     * @param source Bean对象或Map
     * @param targetClass 目标的Bean类型
     * @return Bean对象
     */
    public static <T> T toBean(Object source, Class<T> targetClass) {
        return BeanUtil.toBean(source, targetClass);
    }

    /**
     * 对象转Bean
     *
     * @param source Bean对象或Map
     * @param targetClass 目标的Bean类型
     * @param peek 额外的赋值操作
     * @return Bean对象
     * @param <T> 转换的Bean类型
     */
    public static <T> T toBean(Object source, Class<T> targetClass, Consumer<T> peek) {
        T target = toBean(source, targetClass);
        if (target != null) {
            peek.accept(target);
        }
        return target;
    }

    /**
     * 对象list转 Bean list
     *
     * @param source 原始集合
     * @param targetType 目标类型
     * @return Bean集合
     * @param <S> 原始类型
     * @param <T> 目标类型
     */
    public static <S, T> List<T> toBean(List<S> source, Class<T> targetType) {
        if (CollUtil.isEmpty(source)) {
            return new ArrayList<>();
        }
        return source.stream().map(e -> toBean(e, targetType)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 对象list转 Bean list
     *
     * @param source 原始集合
     * @param targetType 目标类型
     * @param peek 额外的操作
     * @return Bean集合
     * @param <S> 原始类型
     * @param <T> 目标类型
     */
    public static <S, T> List<T> toBean(List<S> source, Class<T> targetType, Consumer<T> peek) {
        List<T> list = toBean(source, targetType);
        if (list != null) {
            list.forEach(peek);
        }
        return list;
    }

    /**
     * 分页对象list转 Bean list
     *
     * @param source 原始集合
     * @param targetType 目标类型
     * @return Bean集合
     * @param <S> 原始类型
     * @param <T> 目标类型
     */
    public static <S, T> PageResult<T> toBean(PageResult<S> source, Class<T> targetType) {
        return toBean(source, targetType, null);
    }

    /**
     * 分页对象list转 Bean list
     *
     * @param source 原始集合
     * @param targetType 目标类型
     * @param peek 额外的操作
     * @return Bean集合
     * @param <S> 原始类型
     * @param <T> 目标类型
     */
    public static <S, T> PageResult<T> toBean(PageResult<S> source, Class<T> targetType, Consumer<T> peek) {
        if (source == null) {
            return null;
        }
        List<T> list = toBean(source.getList(), targetType);
        if (peek != null) {
            list.forEach(peek);
        }
        return new PageResult<>(list, source.getTotal());
    }
}