package com.scut.softwareBigHomework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scut.softwareBigHomework.entity.WorkOrder;
import com.scut.softwareBigHomework.entity.WorkOrderLog;
import com.scut.softwareBigHomework.mapper.WorkOrderLogMapper;
import com.scut.softwareBigHomework.service.WorkOrderLogService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkOrderLogServiceImpl implements WorkOrderLogService {
    @Autowired
    private WorkOrderLogMapper workOrderLogMapper;


    @Override
    public CommonResponse getAllWorkOrderLogs(Integer index) {
        Page<WorkOrderLog> page = new Page<>(index, 10);
        QueryWrapper<WorkOrderLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_at");
        Page<WorkOrderLog> workOrderLogPage = workOrderLogMapper.selectPage(page, null);
        return CommonResponse.success(workOrderLogPage);
    }
}
