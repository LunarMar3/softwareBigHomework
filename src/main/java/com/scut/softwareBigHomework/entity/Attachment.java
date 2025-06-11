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
@TableName("attachments")
public class Attachment {
    int id;

    @TableField("work_order_id")
    int workOrderId;

    @TableField("file_name")
    String fileName;

    @TableField("uploader_id")
    int uploaderId;

    @TableField("created_at")
    LocalDateTime createdAt;
}
