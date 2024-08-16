package com.example.demo.entity;

import com.example.demo.validator.InEnum;
import com.example.demo.enums.GenderEnum;
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
 * 分组校验最佳实践
 * todo 1. 接收参数为List集合,分组校验会失效,需要自定义校验注解, 参考 {@link https://www.cnblogs.com/ancientlian/p/17475320.html}
 * todo 2. 接口mybatis plus 分页操作，创建分页实体等
 *
 * @author yanxingtong
 * @since 2024/8/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity{
    /** 不要使用简单类型比如 int, 它有默认值会导致NotNull没啥作用  */
    @Null(groups = {Save.class}, message = "新增不需要填 id")
    @NotNull(groups = {Update.class}, message = "修改必须要填 id")
    private Integer id;

    @NotBlank(message = "name 不可为空")
    private String name;

    @InEnum(value = GenderEnum.class)
    private Integer gender;

    @Email(message = "email格式错误")
    private String email;

    @Mobile
    private String phone;

    @NotNull(groups = Save.class, message = "新增必须要填 ownerName")
    private String ownerName;
}
