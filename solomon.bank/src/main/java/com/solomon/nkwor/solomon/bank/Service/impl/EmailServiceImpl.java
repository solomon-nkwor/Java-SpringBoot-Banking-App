package com.solomon.nkwor.solomon.bank.Service.impl;

import com.solomon.nkwor.solomon.bank.DTO.EmailDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService{

    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;

    public EmailServiceImpl (JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }



    @Override
    public void sendEmailAlerts(EmailDetails emailDetails) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMessageBody());
            mailMessage.setSubject(emailDetails.getSubject());

            javaMailSender.send(mailMessage);
            log.info("Mail sent successfully!");
        }
        catch(MailException m){
            throw new RuntimeException(m);
        }

    }
}
