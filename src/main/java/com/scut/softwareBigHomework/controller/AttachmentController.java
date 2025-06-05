package com.scut.softwareBigHomework.controller;

import com.scut.softwareBigHomework.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;
}
