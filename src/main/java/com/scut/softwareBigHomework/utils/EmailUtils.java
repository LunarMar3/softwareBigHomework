package com.scut.softwareBigHomework.utils;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmail(String to, String subject, String content) {
        // 创建邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        // 设置收件人
        message.setTo(to);
        // 设置邮件主题
        message.setSubject(subject);
        // 设置邮件内容
        message.setText(content);
        // 发送邮件
        mailSender.send(message);
    }
}
