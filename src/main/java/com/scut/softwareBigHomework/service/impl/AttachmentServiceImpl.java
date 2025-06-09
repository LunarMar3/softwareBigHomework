package com.scut.softwareBigHomework.service.impl;

import com.scut.softwareBigHomework.entity.Attachment;
import com.scut.softwareBigHomework.mapper.AttachmentMapper;
import com.scut.softwareBigHomework.service.AttachmentService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import com.scut.softwareBigHomework.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public CommonResponse uploadAttachment(MultipartFile file,String token,Integer workOrderId) {
        if (file.isEmpty()) {
            return CommonResponse.fail("文件为空");
        }
        String originalFilename = file.getOriginalFilename();
        String filePath = "/root/files/" + originalFilename;
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResponse.fail("文件上传失败");
        }
        Attachment attachment = new Attachment();
        attachment.setUrl(filePath);
        attachment.setFileName(originalFilename);
        attachment.setUploaderId(Integer.parseInt(JwtUtils.getId(token)));
        attachment.setWorkOrderId(workOrderId);
        attachmentMapper.insert(attachment);
        return CommonResponse.success(attachment);
    }
}
