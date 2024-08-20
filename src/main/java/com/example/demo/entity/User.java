package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.enums.GenderEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分组校验最佳实践
 * todo 1. 接收参数为List集合,分组校验会失效,需要自定义校验注解, 参考 <a href="https://www.cnblogs.com/ancientlian/p/17475320.html">...</a>
 *
 * @author yanxingtong
 * @since 2024/8/16
 */
@Data
@TableName("t_test_user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    /** 不要使用简单类型比如 int, 它有默认值会导致@NotNull没啥作用  */
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private String name;

    @TableField
    private GenderEnum gender;

    @TableField
    private String email;

    @TableField
    private String phone;

    @TableField
    private String ownerName;
}
