package com.scut.softwareBigHomework.service.impl;

import com.scut.softwareBigHomework.mapper.DepartmentMapper;
import com.scut.softwareBigHomework.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;
}
