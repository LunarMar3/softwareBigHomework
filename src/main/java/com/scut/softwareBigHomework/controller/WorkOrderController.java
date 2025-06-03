package com.scut.softwareBigHomework.controller;

import com.scut.softwareBigHomework.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/workOrder")
public class WorkOrderController {
    @Autowired
    private WorkOrderService workOrderService;
}
