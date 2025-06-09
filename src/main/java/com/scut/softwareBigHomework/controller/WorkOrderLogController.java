package com.scut.softwareBigHomework.controller;

import com.scut.softwareBigHomework.service.WorkOrderLogService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workOrderLog")
public class WorkOrderLogController {

    @Autowired
    private WorkOrderLogService workOrderLogService;

    @GetMapping("/getAll")
    public CommonResponse getAllWorkOrderLogs(@RequestParam("index")Integer index){
        return workOrderLogService.getAllWorkOrderLogs(index);
    }
}
