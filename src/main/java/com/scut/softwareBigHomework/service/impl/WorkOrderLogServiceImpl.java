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
        QueryWrapper<WorkOrderLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_at");
        int offset = (index - 1) * 10;
        queryWrapper.last("limit " + offset + ", 10");
        return CommonResponse.success(workOrderLogMapper.selectList(queryWrapper));
    }
}
