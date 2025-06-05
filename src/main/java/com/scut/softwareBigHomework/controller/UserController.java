package com.scut.softwareBigHomework.controller;

import com.scut.softwareBigHomework.dto.UserDto;
import com.scut.softwareBigHomework.service.UserService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public CommonResponse login(@RequestBody UserDto userDto) {
        return userService.login(userDto);
    }

    @PostMapping("/register")
    public CommonResponse register(@RequestBody UserDto userDto) {
        return userService.register(userDto);
    }

}
