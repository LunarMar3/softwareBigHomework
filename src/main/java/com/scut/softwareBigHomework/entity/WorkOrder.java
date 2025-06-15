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
    Integer id;

    String title;

    String description;

    String status;

    Integer priority;

    String type;

    @TableField("requester_id")
    Integer requesterId;

    @TableField("department_id")
    Integer departmentId;

    @TableField("assignee_id")
    Integer assigneeId;

    @TableField("assigned_group_id")
    Integer assigneeGroupId;

    @TableField("created_at")
    LocalDateTime createdAt;

    @TableField("updated_at")
    LocalDateTime updatedAt;

    @TableField("completed_at")
    LocalDateTime completedAt;

    LocalDateTime deadline;

    String solution;

    String comment;
}
