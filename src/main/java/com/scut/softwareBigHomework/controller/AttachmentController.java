package com.scut.softwareBigHomework.controller;

import com.scut.softwareBigHomework.service.AttachmentService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/upload")
    public CommonResponse uploadAttachment(@RequestParam("file") MultipartFile file, @RequestHeader("token") String token,@RequestParam("workOrderId") Integer workOrderId) {
        return attachmentService.uploadAttachment(file,token,workOrderId);
    }

    @GetMapping("/getAttachment")
    public CommonResponse getAllAttachments(@RequestHeader("token") String token,@RequestParam("workOrderId") Integer workOrderId) {
        return attachmentService.getAllAttachments(token,workOrderId);
    }
}
