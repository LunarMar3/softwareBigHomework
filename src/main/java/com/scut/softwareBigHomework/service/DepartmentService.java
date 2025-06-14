package com.scut.softwareBigHomework.service;

import com.scut.softwareBigHomework.entity.Department;
import com.scut.softwareBigHomework.utils.CommonResponse;

public interface DepartmentService {
    CommonResponse getAllDepartments();

    CommonResponse addDepartment(Department department);
}
