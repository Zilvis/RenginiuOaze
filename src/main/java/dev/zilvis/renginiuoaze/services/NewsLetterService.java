package dev.zilvis.renginiuoaze.services;

import dev.zilvis.renginiuoaze.models.Events;
import dev.zilvis.renginiuoaze.models.NewsLetter;

import java.io.IOException;
import java.util.List;

public interface NewsLetterService {
    void save(NewsLetter newsletter);
    List<NewsLetter> findAll();
    void sendAll(Events events) throws IOException;
}
