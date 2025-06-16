package com.scut.softwareBigHomework.service;

import com.scut.softwareBigHomework.dto.UserDto;
import com.scut.softwareBigHomework.utils.CommonResponse;

public interface UserService {
    CommonResponse login(UserDto userDto);

    CommonResponse register(UserDto userDto);

    CommonResponse sms(UserDto userDto);

    CommonResponse getUsers(Integer departmentId);

    CommonResponse logout(String token);

    CommonResponse updateDepartmentId(String token,UserDto userDto);
}
