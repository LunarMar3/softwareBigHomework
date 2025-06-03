package com.scut.softwareBigHomework.controller;

import com.scut.softwareBigHomework.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
}
