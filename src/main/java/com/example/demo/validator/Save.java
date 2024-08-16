package com.example.demo.validator;

import javax.validation.groups.Default;

/**
 * 继承 Default 后，没有分组的属性也会被校验，不写分组的属性默认分组为 Default.class
 *
 * @author yanxingtong
 * @since 2024/8/16
 */
public interface Save extends Default {
}
