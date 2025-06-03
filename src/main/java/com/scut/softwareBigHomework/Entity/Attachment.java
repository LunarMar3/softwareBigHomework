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

    @TableField("create_at")
    DateTime createAt;
}
