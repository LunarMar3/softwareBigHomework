package com.scut.softwareBigHomework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scut.softwareBigHomework.entity.WorkOrder;
import com.scut.softwareBigHomework.mapper.WorkOrderMapper;
import com.scut.softwareBigHomework.service.WorkOrderService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import com.scut.softwareBigHomework.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WorkOrderServiceImpl implements WorkOrderService {
    @Autowired
    private WorkOrderMapper workOrderMapper;

    @Override
    public CommonResponse getAllWorkOrders(String token,int index) {
        Jws<Claims> claimsJws = JwtUtils.parseToken(token);
        Claims claims = claimsJws.getBody();
        String id = claims.get("id",String.class);
        QueryWrapper<WorkOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("assignee_id",id);

        queryWrapper.orderByDesc("create_time");
        Page<WorkOrder> page = new Page<>(index, 10);
        Page<WorkOrder> workOrderPage = workOrderMapper.selectPage(page, queryWrapper);
        List<WorkOrder> workOrders = workOrderPage.getRecords();

        return CommonResponse.success(workOrders);

    }
}

