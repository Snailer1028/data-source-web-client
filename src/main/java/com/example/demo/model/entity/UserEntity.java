package com.example.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.enums.GenderEnum;
import com.example.demo.model.entity.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 分组校验最佳实践
 *
 * @author yanxingtong
 * @since 2024/8/16
 */
@Data
@TableName("t_user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {
    /** 不要使用简单类型比如 int, 它有默认值会导致@NotNull没啥作用  */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;
    private String password;
    private GenderEnum gender;
    private String email;
    private String phone;

}
