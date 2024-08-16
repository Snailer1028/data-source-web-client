package com.example.demo.validator;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile, String> {
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[0,1,4-9])|" +
            "(?:5[0-3,5-9])|(?:6[2,5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[0-3,5-9]))\\d{8}$");

    @Override
    public void initialize(Mobile annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果手机号为空，默认不校验，即校验通过
        if (StrUtil.isEmpty(value)) {
            return true;
        }
        // 校验手机
        return StringUtils.hasText(value) && MOBILE_PATTERN.matcher(value).matches();
    }

}
