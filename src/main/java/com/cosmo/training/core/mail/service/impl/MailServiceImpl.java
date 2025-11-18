package com.cosmo.training.core.mail.service.impl;

import com.cosmo.training.core.dto.SendMailRequest;
import com.cosmo.training.core.mail.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl  implements MailService {
    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    @Value("${MAIL_USER}")
    private String sender;

    @Autowired
    private JavaMailSender mailSender;

    @Async
    @Override
    public void sendMail(SendMailRequest sendMailRequest) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(sendMailRequest.getRecipient());
            mimeMessageHelper.setSubject(sendMailRequest.getSubject());
            mimeMessageHelper.setText(sendMailRequest.getMessage(),true);
            mailSender.send(mimeMessage);
        }
        catch (MessagingException e){
            log.error("Failed to send mail",e);
            throw new MailSendException("Failed to send mail. Something went wrong on server.");
        }
    }
}
