package com.scut.softwareBigHomework.controller;

import com.scut.softwareBigHomework.entity.Department;
import com.scut.softwareBigHomework.service.DepartmentService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/getAll")
    public CommonResponse getAllDepartments() {
        return departmentService.getAllDepartments();
    }
}
