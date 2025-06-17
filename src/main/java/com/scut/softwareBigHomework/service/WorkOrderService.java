package com.scut.softwareBigHomework.service;

import com.scut.softwareBigHomework.dto.WorkOrderDto;
import com.scut.softwareBigHomework.utils.CommonResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface WorkOrderService {
    CommonResponse getAllWorkOrders(String token,int index);

    CommonResponse createWorkOrder(String token, WorkOrderDto workOrderDto);

    CommonResponse updateWorkOrder(String token, WorkOrderDto workOrderDto);

    CommonResponse getWorkOrderById(String token, Integer workOrderId);

    CommonResponse closeWorkOrder(String token, WorkOrderDto workOrderDto);

    CommonResponse approveWorkOrder(String token, WorkOrderDto workOrderDto);

    CommonResponse rejectWorkOrder(String token, WorkOrderDto workOrderDto);

    CommonResponse completeWorkOrder(String token, WorkOrderDto workOrderDto);

    CommonResponse assignWorkOrder(String token, WorkOrderDto workOrderDto);

    CommonResponse getAllWorkOrdersByStatus(String token, Integer status, int index);

    CommonResponse finishWorkOrder(String token, WorkOrderDto workOrderDto);
}
