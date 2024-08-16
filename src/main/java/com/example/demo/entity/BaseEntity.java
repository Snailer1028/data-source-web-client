package com.example.demo.entity;

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
    private Long creatorId;

    @Null(message = "不需要填 creatorName")
    private String creatorName;

    @Null(message = "不需要填 updaterId")
    private Long updaterId;

    @Null(message = "不需要填 updaterName")
    private String updaterName;

    @Null(message = "不需要填 createTime")
    private LocalDateTime createTime;

    @Null(message = "不需要填 updateTime")
    private LocalDateTime updateTime;
}
