package dev.zilvis.renginiuoaze.services;

import dev.zilvis.renginiuoaze.models.NewsLetter;

import java.io.IOException;
import java.util.List;

public interface MailService {
    String sendHtmlEmail(String subject, String sendTo, String MailContent) throws IOException;
    String sendMultipleRecipient(String subject, List<NewsLetter> sendTo, String MailContent) throws IOException;
}
