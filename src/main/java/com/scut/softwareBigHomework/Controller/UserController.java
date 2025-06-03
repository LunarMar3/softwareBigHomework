package com.scut.softwareBigHomework.Controller;

import com.scut.softwareBigHomework.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    @Autowired
    private UserService userService;

}
