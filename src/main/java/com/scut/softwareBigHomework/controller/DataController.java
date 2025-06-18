package com.scut.softwareBigHomework.controller;

import com.scut.softwareBigHomework.service.DataService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService dataService;

    @GetMapping("/getTodayProcessWorkOrder")
    public CommonResponse getTodayProcessWorkOrder() {
        return dataService.getTodayProcessWorkOrder();
    }

    @GetMapping("/getDeadlineWorkOrder")
    public CommonResponse getDeadlineWorkOrder() {
        return dataService.getDeadlineWorkOrder();
    }

}
