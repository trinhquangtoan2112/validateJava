package edu.java.helloworld.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import edu.java.helloworld.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine springTemplateEngine;
    @Value("${spring.mail.from}")
    private String emailFrom;

    public String sendEmail(String recipients, String subject, String content, MultipartFile[] files)
            throws MessagingException, UnsupportedEncodingException {
        log.info("Sending ... ");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(emailFrom, "Trịnh Toàn"); // TODO gan email from

        if (recipients.contains(",")) {
            helper.setTo(InternetAddress.parse(recipients));
        } else {
            helper.setTo(recipients);
        }

        if (files != null) {
            for (MultipartFile file : files) {
                helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
            }
        }

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);

        log.info("Send email successfull");
        return "sent";
    }

    @Override
    public String sendConfirmEmail(String email, Long id, String code)
            throws MessagingException, UnsupportedEncodingException {
        log.info("send confirm link");
        // TODO Auto-generated method stub
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

        Context context = new Context();

        String linkConfirm = String.format("http://localhost:8080/users/confirmUser/%s?sercetCode=%s", id, code);
        Map<String, Object> properties = new HashMap<>();
        properties.put("linkConfirm", linkConfirm);
        context.setVariables(properties);
        mimeMessageHelper.setFrom(emailFrom, "Trịnh Quang Toàn");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Please confirm your account");
        String html = springTemplateEngine.process("confirm-email.html", context);
        mimeMessageHelper.setText(html, true);
        mailSender.send(message);
        log.info("email send");
        return "sent";
    }

    @Override
    @KafkaListener(topics = "confirm-account-topic", groupId = "confirm-account-groups")
    public String sendConfirmEmailByKafka(String message)
            throws MessagingException, UnsupportedEncodingException {
        log.info("send confirm link kafka");
        // TODO Auto-generated meth kafod stub
        String[] arr = message.split(",");
        String email = arr[0].substring(arr[0].indexOf("=") + 1);
        String id = arr[1].substring(arr[1].indexOf("=") + 1);
        String code = arr[2].substring(arr[2].indexOf("=") + 1);
        MimeMessage messageMime = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(messageMime,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

        Context context = new Context();

        String linkConfirm = String.format("http://localhost:8080/users/confirmUser/%s?sercetCode=%s", id, code);
        Map<String, Object> properties = new HashMap<>();
        properties.put("linkConfirm", linkConfirm);
        context.setVariables(properties);
        mimeMessageHelper.setFrom(emailFrom, "Trịnh Quang Toàn");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Please confirm your account");
        String html = springTemplateEngine.process("confirm-email.html", context);
        mimeMessageHelper.setText(html, true);
        mailSender.send(messageMime);
        log.info("email send kafka");
        return "sent";
    }
}
