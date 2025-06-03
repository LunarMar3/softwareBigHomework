package com.scut.softwareBigHomework.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("work_orders")
public class WorkOrder {
    int id;

    String title;

    String description;

    String status;

    int priority;

    String type;

    @TableField("requester_id")
    int requesterId;

    @TableField("department_id")
    int departmentId;

    @TableField("assignee_id")
    int assigneeId;

    @TableField("assignee_group_id")
    int assigneeGroupId;

    @TableField("create_at")
    DateTime createAt;

    @TableField("update_at")
    DateTime updateAt;

    @TableField("complete_at")
    DateTime completeAt;

    DateTime deadline;

    String solution;
}
