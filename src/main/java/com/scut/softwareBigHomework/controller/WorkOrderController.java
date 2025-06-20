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

    @GetMapping("/getWorkOrderById")
    public CommonResponse getWorkOrderById(@RequestHeader("token") String token, @RequestParam("workOrderId") Integer workOrderId) {
        return workOrderService.getWorkOrderById(token, workOrderId);
    }

    @PostMapping("/updateWorkOrder")
    public CommonResponse updateWorkOrder(@RequestHeader("token") String token,@RequestBody WorkOrderDto workOrderDto) {
        return workOrderService.updateWorkOrder(token,workOrderDto);
    }

    @PostMapping("/close")
    public CommonResponse closeWorkOrder(@RequestHeader("token") String token, @RequestBody WorkOrderDto workOrderDto) {
        return workOrderService.closeWorkOrder(token, workOrderDto);
    }

    @PostMapping("/approve")
    public CommonResponse approveWorkOrder(@RequestHeader("token") String token, @RequestBody WorkOrderDto workOrderDto) {
        return workOrderService.approveWorkOrder(token, workOrderDto);
    }

    @PostMapping("/reject")
    public CommonResponse rejectWorkOrder(@RequestHeader("token") String token, @RequestBody WorkOrderDto workOrderDto) {
        return workOrderService.rejectWorkOrder(token, workOrderDto);
    }

    @PostMapping("/complete")
    public CommonResponse completeWorkOrder(@RequestHeader("token") String token, @RequestBody WorkOrderDto workOrderDto) {
        return workOrderService.completeWorkOrder(token, workOrderDto);
    }

    @PostMapping("/assign")
    public CommonResponse assignWorkOrder(@RequestHeader("token") String token, @RequestBody WorkOrderDto workOrderDto) {
        return workOrderService.assignWorkOrder(token, workOrderDto);
    }

    @GetMapping("/getAllByStatus")
    public CommonResponse getAllWorkOrdersByStatus(@RequestHeader("token") String token, @RequestParam("status") Integer status, @RequestParam("index") int index) {
        return workOrderService.getAllWorkOrdersByStatus(token, status, index);
    }

    @GetMapping("/getAllByStatusIncludeSelf")
    public CommonResponse getAllWorkOrdersByStatusIncludeSelf(@RequestHeader("token") String token, @RequestParam("status") Integer status, @RequestParam("index") int index) {
        return workOrderService.getAllWorkOrdersByStatusIncludeSelf(token, status, index);
    }

    @PostMapping("/finish")
    public CommonResponse finishWorkOrder(@RequestHeader("token") String token, @RequestBody WorkOrderDto workOrderDto) {
        return workOrderService.finishWorkOrder(token, workOrderDto);
    }

}
