package dev.zilvis.renginiuoaze.services;

import dev.zilvis.renginiuoaze.models.NewsLetter;
import dev.zilvis.renginiuoaze.repository.NewsLetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsLetterServiceImpl implements NewsLetterService{

    private final NewsLetterRepository newsLetterRepository;

    @Override
    public void save(NewsLetter newsletter) {
        newsLetterRepository.save(newsletter);
    }
}
