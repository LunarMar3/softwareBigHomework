package com.scut.softwareBigHomework.entity;

import cn.hutool.core.date.DateTime;
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

    String url;

    @TableField("file_name")
    String fileName;

    @TableField("uploader_id")
    int uploaderId;

    @TableField("created_at")
    LocalDateTime createdAt;
}
