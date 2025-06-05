package com.scut.softwareBigHomework.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String realName;
    private String departmentId;
    private String code;
}
