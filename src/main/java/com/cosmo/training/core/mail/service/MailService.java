package com.cosmo.training.core.mail.service;

import com.cosmo.training.core.dto.SendMailRequest;

public interface MailService {
    void sendMail(SendMailRequest sendMailRequest);
}
