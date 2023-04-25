package com.nineplus.localhand.utils;

import com.nineplus.localhand.dto.UserDto;
import com.nineplus.localhand.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public boolean sendEmail(User user, String subject){
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            Context context = new Context();
            String fullName = user.getFirstName() + " " + user.getLastName();
            context.setVariable("username", fullName);
            String content = templateEngine.process("registration-email", context);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }

    public boolean sendResetPwEmail(User user, String subject, String url){
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            String fullName = user.getFirstName() + " " + user.getLastName();
            Context context = new Context();
            context.setVariable("url", url);
            context.setVariable("username", fullName);
            String content = templateEngine.process("reset-password-email", context);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }

    public boolean sendVerifiedEmail(UserDto user, String subject, String otp){
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            String fullName = user.getFirstName() + " " + user.getLastName();
            Context context = new Context();
            context.setVariable("otp", otp);
            context.setVariable("username", fullName);
            String content = templateEngine.process("verify-otp-email", context);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }
}
