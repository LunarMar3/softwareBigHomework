package com.scut.softwareBigHomework.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("departments")
public class Department {
    int id;

    String name;

    @TableField("parent_id")
    int parentId;

    int level;

    String description;

    @TableField("create_at")
    DateTime createAt;

    @TableField("update_at")
    DateTime updateAt;

}
