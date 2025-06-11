package com.scut.softwareBigHomework.service;

import com.scut.softwareBigHomework.utils.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    CommonResponse uploadAttachment(MultipartFile file,String token,Integer workOrderId);

    CommonResponse getAllAttachments(String token, Integer workOrderId);
}
