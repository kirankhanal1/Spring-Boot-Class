package com.cosmo.training.core.mail.service.impl;

import com.cosmo.training.core.dto.SendMailRequest;
import com.cosmo.training.core.mail.service.EmailTemplateService;
import com.cosmo.training.core.mail.service.MailService;
import com.cosmo.training.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailService mailService;

    @Override
    public void sendWelcomeMail(User user) {
        Context context = new Context();
        context.setVariable("fullName", user.getFullName());
        context.setVariable("email", user.getEmail());
        String message = templateEngine.process("email/welcome-email", context);

        SendMailRequest sendMailRequest = new SendMailRequest();
        sendMailRequest.setRecipient(user.getEmail());
        sendMailRequest.setSubject("Account created");
        sendMailRequest.setMessage(message);
        mailService.sendMail(sendMailRequest);
    }
}
