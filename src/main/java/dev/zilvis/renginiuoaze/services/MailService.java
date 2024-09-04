package dev.zilvis.renginiuoaze.services;

import java.io.IOException;

public interface MailService {
    String sendHtmlEmail(String subject, String sendTo, String MailContent) throws IOException;
}
