package com.scut.softwareBigHomework.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @TableField("assigned_group_id")
    int assigneeGroupId;

    @TableField("created_at")
    LocalDateTime createdAt;

    @TableField("updated_at")
    LocalDateTime updatedAt;

    @TableField("completed_at")
    LocalDateTime completedAt;

    LocalDateTime deadline;

    String solution;
}
