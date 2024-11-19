package com.example.demo.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;

import lombok.Data;

import java.time.LocalDateTime;
import javax.validation.constraints.Null;

/**
 * 这些属性都由服务自身来维护
 *
 * @author yanxingtong
 * @since 2024/8/16
 */
@Data
public class BaseEntity {
    @Null(message = "不需要填 creatorId")
    @TableField
    private Long creatorId;

    @Null(message = "不需要填 updaterId")
    @TableField
    private Long updaterId;

    @Null(message = "不需要填 createTime")
    @TableField
    private LocalDateTime createTime;

    @Null(message = "不需要填 updateTime")
    @TableField
    private LocalDateTime updateTime;
}
