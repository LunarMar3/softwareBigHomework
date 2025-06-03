package com.scut.softwareBigHomework.Entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("work_order_logs")
public class WorkOrderLog {
    int id;

    @TableField("work_order_id")
    int workOrderId;

    @TableField("operator_id")
    int operatorId;

    String action;

    String details;

    @TableField("create_at")
    DateTime createAt;
}
