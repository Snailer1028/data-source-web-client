package com.example.demo.util;

import com.example.demo.exception.ExceptionTypeEnum;
import com.example.demo.exception.ServiceException;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Redis 工具类 - 支持常用的 Redis 操作。
 */
@Component
public class RedisUtils {
    private static final int RESP_ERROR_CODE = HttpStatus.HTTP_INTERNAL_ERROR;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 缓存对象
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void set(String key, T value) {
        this.rejectBlankKey(key);
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存对象并设置过期时间
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     */
    public <T> void set(String key, T value, long timeout, TimeUnit timeUnit) {
        this.rejectBlankKey(key);
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 获取缓存的对象
     *
     * @param key 缓存的键值
     * @param clazz 返回对象的类型
     * @param <T> 泛型
     * @return 缓存对象
     */
    public <T> T get(String key, Class<T> clazz) {
        this.rejectBlankKey(key);
        return clazz.cast(redisTemplate.opsForValue().get(key));
    }

    /**
     * 删除缓存
     *
     * @param key 缓存的键值
     */
    public void delete(String key) {
        this.rejectBlankKey(key);
        redisTemplate.delete(key);
    }

    /**
     * 批量删除缓存
     *
     * @param keys 缓存的键值集合
     */
    public void delete(Collection<String> keys) {
        this.rejectBlankKey(keys);
        redisTemplate.delete(keys);
    }

    /**
     * 检查缓存是否存在
     *
     * @param key 缓存的键值
     * @return 是否存在
     */
    public boolean hasKey(String key) {
        this.rejectBlankKey(key);
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 设置缓存过期时间
     *
     * @param key 缓存的键值
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     */
    public void setExpire(String key, long timeout, TimeUnit timeUnit) {
        this.rejectBlankKey(key);
        redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 获取缓存的过期时间
     *
     * @param key 缓存的键值
     * @param timeUnit 时间单位
     * @return 剩余过期时间
     */
    public Long getExpire(String key, TimeUnit timeUnit) {
        this.rejectBlankKey(key);
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 添加元素到集合中
     *
     * @param key 缓存的键值
     * @param values 元素集合
     */
    public void addToSet(String key, Object... values) {
        this.rejectBlankKey(key);
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 获取集合的所有元素
     *
     * @param key 缓存的键值
     * @return 集合的所有元素
     */
    public Set<Object> getSet(String key) {
        this.rejectBlankKey(key);
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 设置哈希缓存
     *
     * @param key 缓存的键值
     * @param field 哈希字段
     * @param value 哈希值
     */
    public void hSet(String key, String field, Object value) {
        this.rejectBlankKey(key);
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 获取哈希缓存的值
     *
     * @param key 缓存的键值
     * @param field 哈希字段
     * @return 哈希值
     */
    public Object hGet(String key, String field) {
        this.rejectBlankKey(key);
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 批量设置哈希缓存
     *
     * @param key 缓存的键值
     * @param map 哈希字段-值映射
     */
    public void hSetAll(String key, Map<String, Object> map) {
        this.rejectBlankKey(key);
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 删除哈希字段
     *
     * @param key 缓存的键值
     * @param fields 哈希字段集合
     */
    public void hDelete(String key, Object... fields) {
        this.rejectBlankKey(key);
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 自增操作（适用于计数器场景）
     *
     * @param key 缓存的键值
     * @param delta 增量
     * @return 增加后的值
     */
    public Long increment(String key, long delta) {
        this.rejectBlankKey(key);
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 自减操作
     *
     * @param key 缓存的键值
     * @param delta 减量
     * @return 减少后的值
     */
    public Long decrement(String key, long delta) {
        this.rejectBlankKey(key);
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    private void rejectBlankKey(Object key) {
        if (key == null) {
            throw new ServiceException(
                    RESP_ERROR_CODE,
                    "key不能为空",
                    ExceptionTypeEnum.BLANK_REDIS_KEY_TYPE);
        }

        if (key instanceof String) {
            String str = (String) key;
            if (StrUtil.isBlank(str)) {
                throw new ServiceException(
                        RESP_ERROR_CODE,
                        "String类型的key不能为空",
                        ExceptionTypeEnum.BLANK_REDIS_KEY_TYPE);
            }
        } else if (key instanceof Collection<?>) {
            Collection<?> collection = (Collection<?>) key;
            if (CollUtil.isEmpty(collection)) {
                throw new ServiceException(
                        RESP_ERROR_CODE,
                        "Collection类型的key不能为空",
                        ExceptionTypeEnum.BLANK_REDIS_KEY_TYPE);
            }
            for (Object item : collection) {
                if (item == null || (item instanceof String && StrUtil.isBlank((String) item))) {
                    throw new ServiceException(
                            RESP_ERROR_CODE,
                            "Collection中的元素不能为空",
                            ExceptionTypeEnum.BLANK_REDIS_KEY_TYPE);
                }
            }
        } else if (key instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) key;
            if (map.isEmpty()) {
                throw new ServiceException(
                        RESP_ERROR_CODE,
                        "Map类型的key不能为空",
                        ExceptionTypeEnum.BLANK_REDIS_KEY_TYPE);
            }
            for (Object value : map.values()) {
                if (value == null || (value instanceof String && StrUtil.isBlank((String) value))) {
                    throw new ServiceException(
                            RESP_ERROR_CODE,
                            "Map中的值不能为空",
                            ExceptionTypeEnum.BLANK_REDIS_KEY_TYPE);
                }
            }
        } else if (key.getClass().isArray()) {
            Object[] array = (Object[]) key;
            if (array.length == 0) {
                throw new ServiceException(
                        RESP_ERROR_CODE,
                        "Array类型的key不能为空",
                        ExceptionTypeEnum.BLANK_REDIS_KEY_TYPE);
            }
            for (Object item : array) {
                if (item == null || (item instanceof String && StrUtil.isBlank((String) item))) {
                    throw new ServiceException(
                            RESP_ERROR_CODE,
                            "Array中的元素不能为空",
                            ExceptionTypeEnum.BLANK_REDIS_KEY_TYPE);
                }
            }
        } else {
            throw new ServiceException(
                    RESP_ERROR_CODE,
                    "不支持的key类型",
                    ExceptionTypeEnum.UNSUPPORTED_REDIS_KEY_TYPE);
        }
    }

}
