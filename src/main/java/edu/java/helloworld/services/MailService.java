package edu.java.helloworld.services;

import java.io.UnsupportedEncodingException;

import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;

public interface MailService {
    String sendEmail(String recipients, String subject, String content, MultipartFile[] files)
            throws MessagingException, UnsupportedEncodingException;

    String sendConfirmEmail(String email, Long id, String code) throws MessagingException, UnsupportedEncodingException;

    String sendConfirmEmailByKafka(String message)
            throws MessagingException, UnsupportedEncodingException;
}