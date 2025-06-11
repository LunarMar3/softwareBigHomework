package com.scut.softwareBigHomework.controller;

import com.scut.softwareBigHomework.dto.UserDto;
import com.scut.softwareBigHomework.service.UserService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
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

    @PostMapping("/sms")
    public CommonResponse sms(@RequestBody UserDto userDto) {
        return userService.sms(userDto);
    }

    @GetMapping("/getUsersByDepartmentId")
    public CommonResponse getUsers(@RequestParam String departmentId,@RequestParam Integer index) {
        return userService.getUsers(index,departmentId);
    }

    @PostMapping("/logout")
    public CommonResponse logout(@RequestHeader("token") String token) {
        return userService.logout(token);
    }
}
