package com.scut.softwareBigHomework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scut.softwareBigHomework.entity.ApprovalLog;
import com.scut.softwareBigHomework.mapper.ApprovalLogMapper;
import com.scut.softwareBigHomework.service.ApprovalLogService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApprovalLogServiceImpl implements ApprovalLogService {

    @Autowired
    private ApprovalLogMapper approvalLogMapper;


    @Override
    public CommonResponse getAllApprovalLog(Integer index) {
        QueryWrapper<ApprovalLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_at");
        int offset = (index - 1) * 10;
        queryWrapper.last("limit " + offset + ", 10");
        return CommonResponse.success(approvalLogMapper.selectList(queryWrapper));
    }
}
