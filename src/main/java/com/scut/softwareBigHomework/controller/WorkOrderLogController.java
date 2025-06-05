package com.scut.softwareBigHomework.controller;

import com.scut.softwareBigHomework.service.WorkOrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/workOrderLog")
public class WorkOrderLogController {

    @Autowired
    private WorkOrderLogService workOrderLogService;
}
