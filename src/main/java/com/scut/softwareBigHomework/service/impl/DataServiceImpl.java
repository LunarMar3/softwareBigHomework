package com.scut.softwareBigHomework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scut.softwareBigHomework.entity.WorkOrder;
import com.scut.softwareBigHomework.mapper.WorkOrderMapper;
import com.scut.softwareBigHomework.service.DataService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private WorkOrderMapper workOrderMapper;


    @Override
    public CommonResponse getTodayProcessWorkOrder() {
        LambdaQueryWrapper<WorkOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(WorkOrder::getUpdatedAt,
                java.time.LocalDate.now().atStartOfDay(),
                java.time.LocalDate.now().plusDays(1).atStartOfDay());
        queryWrapper.ne(WorkOrder::getStatus, "未处理");
        return CommonResponse.success(
                workOrderMapper.selectCount(queryWrapper)
        );
    }

    @Override
    public CommonResponse getDeadlineWorkOrder() {
        LambdaQueryWrapper<WorkOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(WorkOrder::getDeadline,
                java.time.LocalDate.now().minusDays(1),
                java.time.LocalDate.now());
        queryWrapper.ne(WorkOrder::getStatus, "已完成");
        return CommonResponse.success(
                workOrderMapper.selectCount(queryWrapper)
        );
    }

}
