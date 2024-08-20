package com.example.demo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * enum
 *
 * @author yanxingtong
 * @since 2024/8/16
 */
@AllArgsConstructor
@Getter
public enum GenderEnum implements IntArrayValuable {
    FEMALE(0),
    MALE(1);

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(GenderEnum::getType).toArray();

    @EnumValue
    private final int type;

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
