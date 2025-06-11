package com.scut.softwareBigHomework.controller;

import com.scut.softwareBigHomework.dto.WorkOrderDto;
import com.scut.softwareBigHomework.service.WorkOrderService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workOrder")
public class WorkOrderController {
    @Autowired
    private WorkOrderService workOrderService;

    @GetMapping("/getAll")
    public CommonResponse getAllWorkOrders(@RequestHeader("token") String token,@RequestParam("index") int index) {
        return workOrderService.getAllWorkOrders(token,index);
    }

    @PostMapping("/newWorkOrder")
    public CommonResponse createWorkOrder(@RequestHeader("token") String token,@RequestBody WorkOrderDto workOrderDto) {
        return workOrderService.createWorkOrder(token,workOrderDto);
    }
}
