package com.scut.softwareBigHomework.Controller;

import com.scut.softwareBigHomework.Service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/workOrder")
public class WorkOrderController {
    @Autowired
    private WorkOrderService workOrderService;
}
