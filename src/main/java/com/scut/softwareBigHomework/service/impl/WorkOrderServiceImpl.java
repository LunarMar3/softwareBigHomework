package com.scut.softwareBigHomework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scut.softwareBigHomework.dto.WorkOrderDto;
import com.scut.softwareBigHomework.entity.User;
import com.scut.softwareBigHomework.entity.WorkOrder;
import com.scut.softwareBigHomework.mapper.UserMapper;
import com.scut.softwareBigHomework.mapper.WorkOrderMapper;
import com.scut.softwareBigHomework.service.WorkOrderService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import com.scut.softwareBigHomework.utils.EmailUtils;
import com.scut.softwareBigHomework.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public class WorkOrderServiceImpl implements WorkOrderService {
    @Autowired
    private WorkOrderMapper workOrderMapper;
    @Autowired
    private UserMapper userMapper;

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

    @Override
    public CommonResponse createWorkOrder(String token, WorkOrderDto workOrderDto) {

        Integer id = Integer.valueOf(JwtUtils.getId(token));
        WorkOrder workOrder = new WorkOrder();
        BeanUtils.copyProperties(workOrderDto,workOrder);
        workOrder.setAssigneeId(id);
        workOrder.setCreatedAt(LocalDateTime.now());
        workOrder.setUpdatedAt(LocalDateTime.now());
        workOrderMapper.insert(workOrder);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,workOrderDto.getAssigneeId());
        User user = userMapper.selectOne(queryWrapper);
        Thread.ofVirtual().start(() -> {
            try {
                EmailUtils.sendEmail(user.getEmail(), "新工单", "你有来自id为" + id + "的新工单");
            } catch (Exception e) {
                log.error("邮件发送失败: " + e.getMessage());
            }
        });

        return CommonResponse.success(null);

    }

    @Override
    public CommonResponse updateWorkOrder(String token, WorkOrderDto workOrderDto) {
        Integer id = Integer.valueOf(JwtUtils.getId(token));
        QueryWrapper<WorkOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",workOrderDto.getId());
        queryWrapper.eq("assignee_id",id);
        WorkOrder workOrder = workOrderMapper.selectOne(queryWrapper);
        if (workOrder == null) {
            return CommonResponse.fail("工单不存在");
        }
        return null;




    }
}

