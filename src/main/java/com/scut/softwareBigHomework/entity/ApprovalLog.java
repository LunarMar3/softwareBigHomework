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
@NoArgsConstructor
@AllArgsConstructor
@TableName("approval_logs")
public class ApprovalLog {
    @TableId(type = IdType.AUTO)
    int id;

    @TableField("work_order_id")
    int workOrderId;

    @TableField("approver_id")
    int approverId;

    @TableField("approval_level")
    int approvalLevel;

    int status;

    String comments;

    @TableField("created_at")
    LocalDateTime createdAt;
}
