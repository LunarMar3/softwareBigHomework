package com.scut.softwareBigHomework.controller;


import com.scut.softwareBigHomework.service.ApprovalLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/approvalLog")
public class ApprovalLogController {
    @Autowired
    private ApprovalLogService approvalLogService;
}
