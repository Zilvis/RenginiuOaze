package dev.zilvis.renginiuoaze.controllers;

import dev.zilvis.renginiuoaze.models.NewsLetter;
import dev.zilvis.renginiuoaze.services.NewsLetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail/newsletter")
public class NewsLetterController {

    private final NewsLetterService newsLetterService;

    @PostMapping("/")
    public void subscribeNewsLetter(@RequestBody NewsLetter newsletter) {
        newsLetterService.save(newsletter);
    }
}
