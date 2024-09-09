package dev.zilvis.renginiuoaze.services;

import com.stripe.model.checkout.Session;
import dev.zilvis.renginiuoaze.models.User;

public interface TicketService {
    void createTicketForUser(User user, Session session);
}
