package com.example.demo.util;

import cn.hutool.core.collection.CollUtil;

import java.util.List;

/**
 * class
 *
 * @author yanxingtong
 * @since 2024/8/19
 */
public class CastUtils {
    /**
     * 强转
     *
     * @param obj origin obj
     * @param clazz target class
     * @return target obj
     * @param <T> T
     */
    public static <T> T cast(Object obj, Class<T> clazz) {
        return clazz.cast(obj);
    }

    /**
     * 强转为list
     *
     * @param obj source
     * @param targetType target
     * @param <T> target
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> castObjectToList(Object obj, Class<T> targetType) {
        if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            if (targetType.isInstance(CollUtil.getFirst(list))) {
                return (List<T>) obj;
            }
            throw new IllegalArgumentException("List element type does not match the target type");
        }
        throw new IllegalArgumentException("Object is not a List");
    }
}
