package com.example.demo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import org.springframework.boot.json.JsonParseException;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.concurrent.Callable;

/**
 * 工具类
 *
 * @author yan
 * @since 2024-05-09
 */
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        // 在转换时忽略源对象中不存在的字段
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL); // 忽略 null 值

        SimpleModule simpleModule = new SimpleModule();
        simpleModule
                // 新增 Long 类型序列化规则，数值超过 2^53-1，在 JS 会出现精度丢失问题，因此 Long 自动序列化为字符串类型
                .addSerializer(Long.class, NumberSerializer.INSTANCE)
                .addSerializer(Long.TYPE, NumberSerializer.INSTANCE)
                .addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE)
                .addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE)
                .addSerializer(LocalTime.class, LocalTimeSerializer.INSTANCE)
                .addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE)
                // 新增 LocalDateTime 序列化、反序列化规则，使用 Long 时间戳
                .addSerializer(LocalDateTime.class, TimestampLocalDateTimeSerializer.INSTANCE)
                .addDeserializer(LocalDateTime.class, TimestampLocalDateTimeDeserializer.INSTANCE);
        // 注册到 objectMapper
        OBJECT_MAPPER.registerModule(simpleModule);
    }

    /**
     * 对象转json
     *
     * @param object 入参对象
     * @return json
     */
    public static String objToJsonString(Object object) {
        return parse(() -> OBJECT_MAPPER.writeValueAsString(object));
    }

    /**
     * json转对象
     *
     * @param jsonString json
     * @param targetClass 目标对象
     * @param <T> 目标对象
     * @return 目标对象
     */
    public static <T> T jsonStringToObj(String jsonString, Class<T> targetClass) {
        return parse(() -> OBJECT_MAPPER.readValue(jsonString, targetClass));
    }

    private static <T> T parse(Callable<T> parse) {
        try {
            return parse.call();
        } catch (Exception e) {
            if (JacksonException.class.isAssignableFrom(e.getClass())) {
                throw new JsonParseException(e);
            }
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Map转对象，支持嵌套的Map, 默认多余的字段转化会报错
     *
     * @param obj origin obj
     * @param clazz target class
     * @return target obj
     * @param <T> T
     */
    public static <T> T mapToObj(Object obj, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(obj, clazz);
    }
}

/**
 * Long 序列化规则
 * 会将超长 long 值转换为 string，解决前端 JavaScript 最大安全整数是 2^53-1 的问题
 */
@JacksonStdImpl
class NumberSerializer extends com.fasterxml.jackson.databind.ser.std.NumberSerializer {
    private static final long MAX_SAFE_INTEGER = 9007199254740991L;
    private static final long MIN_SAFE_INTEGER = -9007199254740991L;
    public static final NumberSerializer INSTANCE = new NumberSerializer(Number.class);

    public NumberSerializer(Class<? extends Number> rawType) {
        super(rawType);
    }

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 超出范围 序列化位字符串
        if (value.longValue() > MIN_SAFE_INTEGER && value.longValue() < MAX_SAFE_INTEGER) {
            super.serialize(value, gen, serializers);
        } else {
            gen.writeString(value.toString());
        }
    }
}

/**
 * 基于时间戳的 LocalDateTime 反序列化器
 */
class TimestampLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    public static final TimestampLocalDateTimeDeserializer INSTANCE = new TimestampLocalDateTimeDeserializer();

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // 将 Long 时间戳，转换为 LocalDateTime 对象
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(p.getValueAsLong()), ZoneId.systemDefault());
    }

}

/**
 * 基于时间戳的 LocalDateTime 序列化器
 */
class TimestampLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    public static final TimestampLocalDateTimeSerializer INSTANCE = new TimestampLocalDateTimeSerializer();

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 将 LocalDateTime 对象，转换为 Long 时间戳
        gen.writeNumber(value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

}