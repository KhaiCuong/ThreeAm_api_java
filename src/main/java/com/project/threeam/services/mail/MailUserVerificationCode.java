package com.project.threeam.services.mail;

import com.project.threeam.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class MailUserVerificationCode {
    final String templateMail = "";

    @Autowired
    MailUtil mailUtil;

    /**
     * Send mail notification
     *
     * @param mailTo
     * @param code
     */
    public void sendMail(String mailTo, String content) {
        String subject = "Please verify your registration";
        mailUtil.sendEmailWithForm(mailTo, subject, content);
    }

    public void resetPassMail(String mailTo, String content) {
        String subject = "Reset Password !";
        mailUtil.sendEmailWithForm(mailTo, subject, content);
    }
}
