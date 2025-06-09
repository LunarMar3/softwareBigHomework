package com.scut.softwareBigHomework.service.impl;

import com.scut.softwareBigHomework.mapper.AttachmentMapper;
import com.scut.softwareBigHomework.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentMapper attachmentMapper;


}
