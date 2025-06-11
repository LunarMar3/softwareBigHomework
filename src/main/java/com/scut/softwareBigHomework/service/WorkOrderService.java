package com.scut.softwareBigHomework.service;

import com.scut.softwareBigHomework.dto.WorkOrderDto;
import com.scut.softwareBigHomework.utils.CommonResponse;

public interface WorkOrderService {
    CommonResponse getAllWorkOrders(String token,int index);

    CommonResponse createWorkOrder(String token, WorkOrderDto workOrderDto);
}
