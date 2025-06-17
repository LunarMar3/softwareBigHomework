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
@TableName("department")
public class Department {
    @TableId(type = IdType.AUTO)
    int id;

    String name;

    @TableField("parent_id")
    int parentId;

    int level;

    String description;

    @TableField("create_at")
    LocalDateTime createAt;

    @TableField("update_at")
    LocalDateTime updateAt;

}
