package com.cosmo.training.core.mail.service;

import com.cosmo.training.entity.User;

public interface EmailTemplateService {
    void sendWelcomeMail(User user);
}
