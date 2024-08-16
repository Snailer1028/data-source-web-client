package com.example.demo.util;


import com.example.demo.entity.R;

import org.springframework.http.HttpStatus;

/**
 * class
 *
 * @author yanxingtong
 * @since 2024/8/12
 */
public class ResponseUtils {
    /**
     * success
     *
     * @return ResponseEntity
     */
    public static R<Void> success() {
        return success(null);
    }

    /**
     * success
     *
     * @param data 数据
     * @return ResponseEntity
     * @param <T> data
     */
    public static <T> R<T> success(T data) {
        return success(HttpStatus.OK.value(), "操作成功", data);
    }

    /**
     * success
     *
     * @param data 数据
     * @return ResponseEntity
     * @param <T> data
     */
    public static <T> R<T> success(String msg, T data) {
        return success(HttpStatus.OK.value(), msg, data);
    }

    /**
     * success
     *
     * @param code 状态码
     * @param msg 描述信息
     * @param data 数据
     * @return ResponseEntity
     * @param <T> data
     */
    public static <T> R<T> success(int code, String msg, T data) {
        return R.<T>builder().code(code).msg(msg).data(data).build();
    }

    /**
     * 异常
     *
     * @param msg 描述信息
     * @return ResponseEntity
     */
    public static R<Void> error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    /**
     * 异常
     *
     * @param code 状态码
     * @param msg 描述信息
     * @return ResponseEntity
     * @param <T> data
     */
    public static <T> R<T> error(int code, String msg) {
        return error(code, msg, null);
    }

    /**
     * 异常
     *
     * @param code 状态码
     * @param msg 描述信息
     * @param data 数据
     * @return ResponseEntity
     * @param <T> data
     */
    public static <T> R<T> error(int code, String msg, T data) {
        return R.<T>builder().code(code).msg(msg).data(data).build();
    }
}
