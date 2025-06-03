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
@TableName("users")
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

    @TableField("create_time")
    DateTime createTime;

    @TableField("update_time")
    DateTime updateTime;

    String role;

    @TableField("is_active")
    boolean isActive;

    @TableField("is_delete")
    boolean isDelete;
}
