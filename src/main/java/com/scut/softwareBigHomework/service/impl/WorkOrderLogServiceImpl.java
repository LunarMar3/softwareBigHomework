package com.scut.softwareBigHomework.service.impl;

import com.scut.softwareBigHomework.mapper.WorkOrderLogMapper;
import com.scut.softwareBigHomework.service.WorkOrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkOrderLogServiceImpl implements WorkOrderLogService {
    @Autowired
    private WorkOrderLogMapper workOrderLogMapper;


}
