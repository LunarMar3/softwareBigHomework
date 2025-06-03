package com.scut.softwareBigHomework.Entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("approval_logs")
public class ApprovalLog {
    int id;

    @TableField("work_order_id")
    int workOrderId;

    @TableField("approver_id")
    int approverId;

    @TableField("approval_level")
    int approvalLevel;

    int status;

    String comment;

    @TableField("create_at")
    DateTime createAt;
}
