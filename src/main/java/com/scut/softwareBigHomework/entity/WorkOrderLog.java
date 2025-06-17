package com.scut.softwareBigHomework.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("work_order_logs")
public class WorkOrderLog {
    @TableId(type = IdType.AUTO)
    int id;

    @TableField("work_order_id")
    int workOrderId;

    @TableField("operator_id")
    int operatorId;

    String action;

    String details;

    @TableField("created_at")
    LocalDateTime createdAt;
}
