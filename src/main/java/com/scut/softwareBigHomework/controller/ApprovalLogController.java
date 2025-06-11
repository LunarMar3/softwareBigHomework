package com.scut.softwareBigHomework.controller;


import com.scut.softwareBigHomework.service.ApprovalLogService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/approvalLog")
public class ApprovalLogController {
    @Autowired
    private ApprovalLogService approvalLogService;

    @GetMapping("/getAll")
    public CommonResponse getAllApprovalLog(@RequestParam("index") Integer index) {
        return approvalLogService.getAllApprovalLog(index);
    }

}
