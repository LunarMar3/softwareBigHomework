package com.scut.softwareBigHomework.service;

import com.scut.softwareBigHomework.dto.UserDto;
import com.scut.softwareBigHomework.utils.CommonResponse;

public interface UserService {
    CommonResponse login(UserDto userDto);

    CommonResponse register(UserDto userDto);

    CommonResponse sms(UserDto userDto);

    CommonResponse getUsers(Integer index ,String departmentId);

    CommonResponse logout(String token);
}
