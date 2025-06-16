package com.scut.softwareBigHomework.service.impl;

import com.scut.softwareBigHomework.service.DataService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public CommonResponse getTodayProcessWorkOrder() {
        return null;
    }
}
