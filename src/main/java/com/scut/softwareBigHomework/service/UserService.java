package com.scut.softwareBigHomework.service;

import com.scut.softwareBigHomework.dto.UserDto;
import com.scut.softwareBigHomework.utils.CommonResponse;

public interface UserService {
    CommonResponse login(UserDto userDto);

    CommonResponse register(UserDto userDto);
}
