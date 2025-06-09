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
@TableName("user")
public class User {
    int id;

    String username;

    String password;

    @TableField("real_name")
    String realName;

    String email;

    @TableField("phone_number")
    String phone;

    @TableField("department_id")
    String departmentId;

    @TableField("created_at")
    DateTime createdAt;

    @TableField("updated_at")
    DateTime updatedAt;

    String role;

    @TableField("is_active")
    boolean isActive;

    @TableField("is_deleted")
    boolean isDeleted;
}
