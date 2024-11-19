package com.example.demo.entity;

import com.example.demo.enums.GenderEnum;
import com.example.demo.validator.InEnum;
import com.example.demo.validator.Mobile;
import com.example.demo.validator.Save;
import com.example.demo.validator.Update;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 分组校验最佳实践 //todo 批量操作有问题
 *
 * @author yanxingtong
 * @since 2024/8/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends PageParam{
    /** 不要使用简单类型比如 int, 它有默认值会导致@NotNull没啥作用  */
    @Null(groups = {Save.class}, message = "新增不需要填 id")
    @NotNull(groups = {Update.class}, message = "必须要填 id")
    private Integer id;

    @NotBlank(groups = {Save.class}, message = "name 不可为空")
    private String username;

    private String password;

    @InEnum(value = GenderEnum.class)
    private Integer gender;

    @Email(message = "email格式错误")
    private String email;

    @Mobile
    private String phone;
}
