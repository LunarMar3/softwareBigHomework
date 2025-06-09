package com.scut.softwareBigHomework.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    int departmentId;

    @TableField("created_at")
    LocalDateTime createdAt;

    @TableField("updated_at")
    LocalDateTime updatedAt;

    String role;

    @TableField("is_active")
    boolean isActive;

    @TableField("is_deleted")
    boolean isDeleted;
}
