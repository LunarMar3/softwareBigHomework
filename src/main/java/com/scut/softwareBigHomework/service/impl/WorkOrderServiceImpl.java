package com.scut.softwareBigHomework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scut.softwareBigHomework.dto.WorkOrderDto;
import com.scut.softwareBigHomework.entity.User;
import com.scut.softwareBigHomework.entity.WorkOrder;
import com.scut.softwareBigHomework.entity.WorkOrderLog;
import com.scut.softwareBigHomework.mapper.ApprovalLogMapper;
import com.scut.softwareBigHomework.mapper.UserMapper;
import com.scut.softwareBigHomework.mapper.WorkOrderLogMapper;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public class WorkOrderServiceImpl implements WorkOrderService {
    @Autowired
    private WorkOrderMapper workOrderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ApprovalLogMapper approvalLogMapper;
    @Autowired
    private WorkOrderLogMapper workOrderLogMapper;
    @Autowired
    private EmailUtils emailUtils;

    @Override
    public CommonResponse getAllWorkOrders(String token,int index) {
        String id = JwtUtils.getId(token);
        QueryWrapper<WorkOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("assignee_id",id);
        queryWrapper.or().eq("requester_id",id);

        queryWrapper.orderByDesc("updated_at");
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
                emailUtils.sendEmail(user.getEmail(), "新工单", "你有来自id为" + id + "的新工单");
            } catch (Exception e) {
                log.error("邮件发送失败: " + e.getMessage());
            }
        });

        WorkOrderLog workOrderLog = new WorkOrderLog();
        workOrderLog.setWorkOrderId(workOrder.getId());
        workOrderLog.setAction("创建工单");
        workOrderLog.setCreatedAt(LocalDateTime.now());
        workOrderLog.setOperatorId(id);
        return CommonResponse.success(null);

    }

    @Override
    public CommonResponse updateWorkOrder(String token, WorkOrderDto workOrderDto) {
        Integer id = Integer.valueOf(JwtUtils.getId(token));
        QueryWrapper<WorkOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",workOrderDto.getId());
        WorkOrder workOrder = workOrderMapper.selectOne(queryWrapper);
        if (workOrder == null) {
            return CommonResponse.fail("工单不存在");
        }
        if (!workOrder.getAssigneeId().equals(id)) {
            return CommonResponse.fail("你没有权限修改此工单");
        }
        if (workOrder.getStatus().equals("已关闭")) {
            return CommonResponse.fail("工单已关闭，无法修改");
        }
        if (workOrder.getStatus().equals("已完成")) {
            return CommonResponse.fail("工单已完成，无法修改");
        }
        if (workOrderDto.getStatus() != null) {
            workOrder.setStatus(workOrderDto.getStatus());
        }
        if (workOrderDto.getPriority() != null) {
            workOrder.setPriority(workOrderDto.getPriority());
        }
        if (workOrderDto.getDeadline() != null) {
            workOrder.setDeadline(workOrderDto.getDeadline());
        }
        if (workOrderDto.getDescription() != null) {
            workOrder.setDescription(workOrderDto.getDescription());
        }
        if (workOrderDto.getComment() != null) {
            workOrder.setComment(workOrderDto.getComment());
        }
        workOrder.setUpdatedAt(LocalDateTime.now());
        workOrderMapper.updateById(workOrder);
        return CommonResponse.success("工单已更新");
    }

    @Override
    public CommonResponse getWorkOrderById(String token, Integer workOrderId) {
        LambdaQueryWrapper<WorkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkOrder::getId, workOrderId);
        WorkOrder workOrder = workOrderMapper.selectOne(lambdaQueryWrapper);
        if (workOrder == null) {
            return CommonResponse.fail("工单不存在");
        }
        return CommonResponse.success(workOrder);
    }

    @Override
    public CommonResponse closeWorkOrder(String token, WorkOrderDto workOrderDto) {
        LambdaQueryWrapper<WorkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkOrder::getId, workOrderDto.getId());
        WorkOrder workOrder = workOrderMapper.selectOne(lambdaQueryWrapper);
        if (workOrder == null) {
            return CommonResponse.fail("工单不存在");
        }
        workOrder.setStatus("已关闭");
        workOrderMapper.updateById(workOrder);
        return CommonResponse.success("工单已关闭");
    }

    @Override
    public CommonResponse approveWorkOrder(String token, WorkOrderDto workOrderDto) {
        LambdaQueryWrapper<WorkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkOrder::getId, workOrderDto.getId());
        WorkOrder workOrder = workOrderMapper.selectOne(lambdaQueryWrapper);
        if (workOrder == null) {
            return CommonResponse.fail("工单不存在");
        }
        if (!workOrder.getStatus().equals("已完成")) {
            return CommonResponse.fail("工单未完成，无法审批");
        }
        if (workOrder.getSolution() == null || workOrder.getSolution().isEmpty()) {
            return CommonResponse.fail("工单未提供解决方案，无法审批");
        }
        workOrder.setStatus("已审批");
        workOrder.setComment(workOrderDto.getComment());
        workOrderMapper.updateById(workOrder);
        emailUtils.sendEmail(
                userMapper.selectById(workOrder.getRequesterId()).getEmail(),
                "工单审批结果",
                "你的工单已被审批，状态为：已审批"
        );
        return CommonResponse.success("工单已审批");
    }

    @Override
    public CommonResponse rejectWorkOrder(String token, WorkOrderDto workOrderDto) {
        LambdaQueryWrapper<WorkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkOrder::getId, workOrderDto.getId());
        WorkOrder workOrder = workOrderMapper.selectOne(lambdaQueryWrapper);
        if (workOrder == null) {
            return CommonResponse.fail("工单不存在");
        }
        if (!workOrder.getStatus().equals("已完成")) {
            return CommonResponse.fail("工单未完成，无法拒绝");
        }
        workOrder.setStatus("已拒绝");
        workOrder.setComment(workOrderDto.getComment());
        workOrderMapper.updateById(workOrder);
        emailUtils.sendEmail(
                userMapper.selectById(workOrder.getRequesterId()).getEmail(),
                "工单审批结果",
                "你的工单已被拒绝，状态为：已拒绝，请联系相关人员处理"
        );
        return CommonResponse.success("工单已拒绝");
    }

    @Override
    public CommonResponse completeWorkOrder(String token, WorkOrderDto workOrderDto) {
        LambdaQueryWrapper<WorkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkOrder::getId, workOrderDto.getId());
        WorkOrder workOrder = workOrderMapper.selectOne(lambdaQueryWrapper);
        if (workOrder == null) {
            return CommonResponse.fail("工单不存在");
        }
        workOrder.setStatus("已完成预案");
        workOrder.setSolution(workOrderDto.getSolution());
        workOrder.setComment(workOrderDto.getComment());
        workOrderMapper.updateById(workOrder);
        emailUtils.sendEmail(
                userMapper.selectById(workOrder.getRequesterId()).getEmail(),
                "工单完成通知",
                "你的工单已完成预案，状态为：已完成，请等待上级审批是否通过该解决方案。"
        );
        return CommonResponse.success("工单已完成预案");
    }
}

