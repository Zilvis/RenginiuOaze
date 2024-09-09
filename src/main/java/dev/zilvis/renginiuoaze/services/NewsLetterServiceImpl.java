package dev.zilvis.renginiuoaze.services;

import dev.zilvis.renginiuoaze.models.Events;
import dev.zilvis.renginiuoaze.models.NewsLetter;
import dev.zilvis.renginiuoaze.repository.NewsLetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsLetterServiceImpl implements NewsLetterService{

    private final NewsLetterRepository newsLetterRepository;
    private final MailService mailService;

    @Override
    public void save(NewsLetter newsletter) {
        newsLetterRepository.save(newsletter);
    }

    @Override
    public List<NewsLetter> findAll() {
        return newsLetterRepository.findAll();
    }

    @Override
    public void sendAll(Events events) throws IOException {
        List<NewsLetter> subscribers = newsLetterRepository.findAll();
        String subject = events.getTitle();
        String description = events.getDescription();
        mailService.sendMultipleRecipient(subject,subscribers,description);
    }
}
