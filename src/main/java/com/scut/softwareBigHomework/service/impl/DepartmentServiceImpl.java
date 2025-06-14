package com.scut.softwareBigHomework.service.impl;

import com.scut.softwareBigHomework.entity.Department;
import com.scut.softwareBigHomework.mapper.DepartmentMapper;
import com.scut.softwareBigHomework.service.DepartmentService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public CommonResponse getAllDepartments() {
        List<Department> departments = departmentMapper.selectList(null);
        return CommonResponse.success(departments);
    }

    @Override
    public CommonResponse addDepartment(Department department) {
        department.setCreateAt(LocalDateTime.now());
        department.setUpdateAt(LocalDateTime.now());
        departmentMapper.insert(department);
        return CommonResponse.success(department);
    }
}
