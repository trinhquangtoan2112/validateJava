package edu.java.helloworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import edu.java.helloworld.dto.response.ResponseData;
import edu.java.helloworld.dto.response.ResponseError;
import edu.java.helloworld.services.MailService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/common")
@RestController
@Slf4j
public class CommonControler {

    @Autowired
    private MailService mailService;

    @PostMapping("/send-email")
    public ResponseData<String> sendEmail(@RequestParam String recipients, @RequestParam String subject,
            @RequestParam String content,
            @RequestParam(required = false) MultipartFile[] files) {
        try {
            return new ResponseData<>(HttpStatus.ACCEPTED.value(),
                    mailService.sendEmail(recipients, subject, content, files));
        } catch (Exception e) {
            log.error("Loi ", e);
            return new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "loi xay ra");
        }
    }

}