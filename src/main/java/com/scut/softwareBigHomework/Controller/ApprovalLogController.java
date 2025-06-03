package com.scut.softwareBigHomework.Controller;


import com.scut.softwareBigHomework.Service.ApprovalLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/approvalLog")
public class ApprovalLogController {
    @Autowired
    private ApprovalLogService approvalLogService;
}
