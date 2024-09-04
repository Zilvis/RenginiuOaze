package dev.zilvis.renginiuoaze.controllers;

import dev.zilvis.renginiuoaze.services.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class MailSenderController {


    private final MailService mailService;

    // TODO
//    @PostMapping("/send-text")
//    public String send() throws IOException, IOException {
//        return mailService.sendHtmlEmail()
//    }
}
