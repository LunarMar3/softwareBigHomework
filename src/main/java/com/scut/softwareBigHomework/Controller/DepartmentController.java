package com.scut.softwareBigHomework.Controller;

import com.scut.softwareBigHomework.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
}
