package dev.zilvis.renginiuoaze.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import com.sendgrid.helpers.mail.objects.Personalization;
import dev.zilvis.renginiuoaze.models.NewsLetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class MailServiceImpl implements MailService{

    @Value("${spring.sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${spring.sendgrid.email}")
    private String email;

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Override
    public String sendHtmlEmail(String subject, String sendTo, String MailContent) throws IOException {
        Email from = new Email(email);
        Email to = new Email(sendTo);
        Content content = new Content("text/plain", MailContent);

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        return getString(mail, sg);
    }

    @Override
    public String sendMultipleRecipient(String subject, List<NewsLetter> sendTo, String MailContent) throws IOException {
        Email from = new Email(this.email);
        String contentType = "text/html";
        Content content = new Content(contentType, MailContent);
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.addContent(content);

        Personalization personalization = new Personalization();

        for (NewsLetter recipient : sendTo) {
            Email to = new Email(recipient.getEmail());
            personalization.addTo(to);
        }

        mail.addPersonalization(personalization);

        SendGrid sg = new SendGrid(this.sendGridApiKey);
        return getString(mail, sg);
    }

    private String getString(Mail mail, SendGrid sg) throws IOException {
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            logger.info(response.getBody());
            return response.getBody();
        } catch (IOException ex) {
            throw ex;
        }
    }
}