package com.scut.softwareBigHomework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String realName;
    private Integer departmentId;
    private Integer leaderId;
    private String code;
}
