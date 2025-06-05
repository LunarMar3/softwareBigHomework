package com.scut.softwareBigHomework.service;

import com.scut.softwareBigHomework.utils.CommonResponse;

public interface WorkOrderService {
    CommonResponse getAllWorkOrders(String token,int index);
}
