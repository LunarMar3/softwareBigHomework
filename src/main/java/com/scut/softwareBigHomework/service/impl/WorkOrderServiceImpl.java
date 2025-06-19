package com.scut.softwareBigHomework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scut.softwareBigHomework.dto.WorkOrderDto;
import com.scut.softwareBigHomework.entity.ApprovalLog;
import com.scut.softwareBigHomework.entity.User;
import com.scut.softwareBigHomework.entity.WorkOrder;
import com.scut.softwareBigHomework.entity.WorkOrderLog;
import com.scut.softwareBigHomework.enums.StatusEnum;
import com.scut.softwareBigHomework.mapper.ApprovalLogMapper;
import com.scut.softwareBigHomework.mapper.UserMapper;
import com.scut.softwareBigHomework.mapper.WorkOrderLogMapper;
import com.scut.softwareBigHomework.mapper.WorkOrderMapper;
import com.scut.softwareBigHomework.service.WorkOrderService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import com.scut.softwareBigHomework.utils.EmailUtils;
import com.scut.softwareBigHomework.utils.JwtUtils;
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
    @Autowired
    private ApprovalLogMapper approvalLogMapper;
    @Autowired
    private WorkOrderLogMapper workOrderLogMapper;
    @Autowired
    private EmailUtils emailUtils;

    @Override
    public CommonResponse getAllWorkOrders(String token, int index) {
        Integer userId = Integer.parseInt(JwtUtils.getId(token));
        LambdaQueryWrapper<WorkOrder> queryWrapper = new LambdaQueryWrapper<>();
        // 使用Lambda表达式，更清晰的OR条件组合
        queryWrapper.and(wrapper ->
                wrapper.eq(WorkOrder::getAssigneeId, userId)
                        .or()
                        .eq(WorkOrder::getRequesterId, userId)
        );
        queryWrapper.orderByDesc(WorkOrder::getUpdatedAt);
        int offset = (index - 1) * 10;
        queryWrapper.last("limit " + offset + ", 10");


        return CommonResponse.success(workOrderMapper.selectList(queryWrapper));
    }

    @Override
    public CommonResponse createWorkOrder(String token, WorkOrderDto workOrderDto) {

        int id = Integer.parseInt(JwtUtils.getId(token));
        User user = userMapper.selectById(id);
        WorkOrder workOrder = new WorkOrder();
        BeanUtils.copyProperties(workOrderDto, workOrder);
        workOrder.setAssigneeId(id);
        workOrder.setStatus("未处理");
        workOrder.setCreatedAt(LocalDateTime.now());
        workOrder.setUpdatedAt(LocalDateTime.now());
        workOrder.setRequesterId(id);
        workOrder.setAssigneeId(user.getLeaderId());
        workOrderMapper.insert(workOrder);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, workOrderDto.getAssigneeId());
        Thread.ofVirtual().start(() -> {
            try {
                emailUtils.sendEmail(userMapper.selectOne(queryWrapper).getEmail(), "新工单", "你有来自id为" + id + "的新工单");
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
        queryWrapper.eq("id", workOrderDto.getId());
        WorkOrder workOrder = workOrderMapper.selectOne(queryWrapper);
        if (workOrder == null) {
            return CommonResponse.fail("工单不存在");
        }
        if (!workOrder.getAssigneeId().equals(id) || !workOrder.getRequesterId().equals(id)) {
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
        WorkOrderLog workOrderLog = new WorkOrderLog();
        workOrderLog.setWorkOrderId(workOrder.getId());
        workOrderLog.setAction("修改工单");
        workOrderLog.setCreatedAt(LocalDateTime.now());
        workOrderLog.setOperatorId(id);
        workOrderLogMapper.insert(workOrderLog);

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
        WorkOrderLog workOrderLog = new WorkOrderLog();
        workOrderLog.setWorkOrderId(workOrder.getId());
        workOrderLog.setAction("关闭工单");
        workOrderLog.setCreatedAt(LocalDateTime.now());
        workOrderLog.setOperatorId(Integer.parseInt(JwtUtils.getId(token)));
        workOrderLogMapper.insert(workOrderLog);
        return CommonResponse.success("工单已关闭");
    }

    @Override
    public CommonResponse approveWorkOrder(String token, WorkOrderDto workOrderDto) {
        User user = userMapper.selectById(Integer.parseInt(JwtUtils.getId(token)));
        LambdaQueryWrapper<WorkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkOrder::getId, workOrderDto.getId());
        WorkOrder workOrder = workOrderMapper.selectOne(lambdaQueryWrapper);
        if (workOrder == null) {
            return CommonResponse.fail("工单不存在");
        }
        if (workOrder.getSolution() == null || workOrder.getSolution().isEmpty()) {
            return CommonResponse.fail("工单未提供解决方案，无法审批");
        }
        if (workOrder.getStatus().equals("已关闭")) {
            return CommonResponse.fail("工单已关闭，无法审批");
        }

        if (user.getLeaderId()!=null) {
            workOrder.setStatus("审批中");
            workOrder.setComment(workOrderDto.getComment());
            workOrder.setAssigneeId(user.getLeaderId());
            workOrderMapper.updateById(workOrder);
            LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
            userQueryWrapper.eq(User::getId, workOrder.getAssigneeId());
            emailUtils.sendEmail(
                    userMapper.selectOne(userQueryWrapper).getEmail(),
                    "工单审批结果",
                    "你的工单已被审批，请等待下一步审批"
            );
            ApprovalLog approvalLog = new ApprovalLog();
            approvalLog.setWorkOrderId(workOrder.getId());
            approvalLog.setApproverId(Integer.parseInt(JwtUtils.getId(token)));
            approvalLog.setCreatedAt(LocalDateTime.now());
            approvalLog.setStatus(1);
            approvalLog.setComments(workOrderDto.getComment());
            approvalLogMapper.insert(approvalLog);
        }else {
            workOrder.setStatus("已审批");
            workOrder.setComment(workOrderDto.getComment());
            workOrder.setAssigneeId(workOrderDto.getOperatorId());
            workOrderMapper.updateById(workOrder);
            LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
            userQueryWrapper.eq(User::getId, workOrder.getRequesterId());
            emailUtils.sendEmail(
                    userMapper.selectOne(userQueryWrapper).getEmail(),
                    "工单审批结果",
                    "你的工单已审批完成，请等待操作人员处理"
            );
            ApprovalLog approvalLog = new ApprovalLog();
            approvalLog.setWorkOrderId(workOrder.getId());
            approvalLog.setApproverId(Integer.parseInt(JwtUtils.getId(token)));
            approvalLog.setCreatedAt(LocalDateTime.now());
            approvalLog.setStatus(1);
            approvalLog.setComments(workOrderDto.getComment());
            approvalLogMapper.insert(approvalLog);
        }
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
        if (!workOrder.getStatus().equals("审批中")) {
            return CommonResponse.fail("工单未完成预案，无法拒绝");
        }
        workOrder.setStatus("已拒绝");
        workOrder.setComment(workOrderDto.getComment());
        workOrderMapper.updateById(workOrder);
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getId, workOrder.getAssigneeId());
        emailUtils.sendEmail(
                userMapper.selectOne(userQueryWrapper).getEmail(),
                "工单审批结果",
                "你的工单已被拒绝，请联系相关人员处理"
        );
        ApprovalLog approvalLog = new ApprovalLog();
        approvalLog.setWorkOrderId(workOrder.getId());
        approvalLog.setApproverId(Integer.parseInt(JwtUtils.getId(token)));
        approvalLog.setCreatedAt(LocalDateTime.now());
        approvalLog.setStatus(0);
        approvalLog.setComments(workOrderDto.getComment());
        approvalLogMapper.insert(approvalLog);
        return CommonResponse.success("工单已拒绝");
    }

    @Override
    public CommonResponse completeWorkOrder(String token, WorkOrderDto workOrderDto) {
        User user = userMapper.selectById(Integer.parseInt(JwtUtils.getId(token)));
        LambdaQueryWrapper<WorkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkOrder::getId, workOrderDto.getId());
        WorkOrder workOrder = workOrderMapper.selectOne(lambdaQueryWrapper);
        if (workOrder == null) {
            return CommonResponse.fail("工单不存在");
        }
        workOrder.setStatus("审批中");
        workOrder.setSolution(workOrderDto.getSolution());
        workOrder.setComment(workOrderDto.getComment());
        workOrder.setAssigneeId(user.getLeaderId());
        workOrderMapper.updateById(workOrder);
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getId, workOrder.getAssigneeId());
        emailUtils.sendEmail(
                userMapper.selectOne(userQueryWrapper).getEmail(),
                "工单完成通知",
                "你的工单已完成预案，状态为：审批中，请等待流转。"
        );
        WorkOrderLog workOrderLog = new WorkOrderLog();
        workOrderLog.setWorkOrderId(workOrder.getId());
        workOrderLog.setAction("工单完成预案");
        workOrderLog.setCreatedAt(LocalDateTime.now());
        workOrderLog.setOperatorId(Integer.parseInt(JwtUtils.getId(token)));
        workOrderLogMapper.insert(workOrderLog);
        return CommonResponse.success("工单已完成预案，审批中");
    }

    @Override
    public CommonResponse assignWorkOrder(String token, WorkOrderDto workOrderDto) {
        LambdaQueryWrapper<WorkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkOrder::getId, workOrderDto.getId());
        WorkOrder workOrder = workOrderMapper.selectOne(lambdaQueryWrapper);
        if (workOrder == null) {
            return CommonResponse.fail("工单不存在");
        }
        if (!workOrder.getAssigneeId().equals(Integer.parseInt(JwtUtils.getId(token)))) {
            return CommonResponse.fail("你没有权限指派此工单");
        }
        if (workOrder.getStatus().equals("已关闭") || workOrder.getStatus().equals("已完成")) {
            return CommonResponse.fail("工单已关闭或已完成，无法指派");
        }
        workOrder.setStatus("已指派");
        workOrder.setAssigneeId(workOrderDto.getAssigneeId());
        workOrder.setUpdatedAt(LocalDateTime.now());
        workOrderMapper.updateById(workOrder);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id", workOrderDto.getAssigneeId());
        emailUtils.sendEmail(
                userMapper.selectOne(userQueryWrapper).getEmail(),
                "工单指派通知",
                "你已被指派处理工单，工单ID：" + workOrder.getId() + "，请尽快处理。"
        );
        WorkOrderLog workOrderLog = new WorkOrderLog();
        workOrderLog.setWorkOrderId(workOrder.getId());
        workOrderLog.setAction("指派工单");
        workOrderLog.setCreatedAt(LocalDateTime.now());
        workOrderLog.setOperatorId(Integer.parseInt(JwtUtils.getId(token)));
        workOrderLogMapper.insert(workOrderLog);
        return CommonResponse.success("工单已指派给用户ID：" + workOrderDto.getAssigneeId());
    }

    @Override
    public CommonResponse getAllWorkOrdersByStatus(String token, Integer status, int index) {
        Integer userId = Integer.parseInt(JwtUtils.getId(token));
        LambdaQueryWrapper<WorkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkOrder::getStatus, StatusEnum.getStatusString(status));
        lambdaQueryWrapper.eq(WorkOrder::getAssigneeId,userId);
        lambdaQueryWrapper.orderByDesc(WorkOrder::getUpdatedAt);
        int offset = (index - 1) * 10;
        lambdaQueryWrapper.last("limit " + offset + ", 10");

        return CommonResponse.success(workOrderMapper.selectList(lambdaQueryWrapper));
    }

    @Override
    public CommonResponse finishWorkOrder(String token, WorkOrderDto workOrderDto) {
        int userId = Integer.parseInt(JwtUtils.getId(token));
        LambdaQueryWrapper<WorkOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WorkOrder::getId, workOrderDto.getId());
        WorkOrder workOrder = workOrderMapper.selectOne(lambdaQueryWrapper);
        if (workOrder == null) {
            return CommonResponse.fail("工单不存在");
        }
        if (!workOrder.getAssigneeId().equals(userId)) {
            return CommonResponse.fail("工单不属于你，无法完成");
        }
        workOrder.setStatus("已完成");
        workOrder.setUpdatedAt(LocalDateTime.now());
        workOrderMapper.updateById(workOrder);
        WorkOrderLog workOrderLog = new WorkOrderLog();
        workOrderLog.setWorkOrderId(workOrder.getId());
        workOrderLog.setAction("完成工单");
        workOrderLog.setCreatedAt(LocalDateTime.now());
        workOrderLog.setOperatorId(userId);
        workOrderLogMapper.insert(workOrderLog);

        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getId, workOrder.getAssigneeId());
        emailUtils.sendEmail(
                userMapper.selectOne(userQueryWrapper).getEmail(),
                "工单处理结果",
                "你id为" + userId + "的工单已完成，请查看详情。"
        );

        return CommonResponse.success("工单已完成");
    }
}

