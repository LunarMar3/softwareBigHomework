package com.scut.softwareBigHomework.service;

import com.scut.softwareBigHomework.utils.CommonResponse;

public interface DataService {
    CommonResponse getTodayProcessWorkOrder();

    CommonResponse getDeadlineWorkOrder();
}
