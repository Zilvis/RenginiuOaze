package dev.zilvis.renginiuoaze.services;

import com.stripe.model.checkout.Session;
import dev.zilvis.renginiuoaze.models.Tickets;
import dev.zilvis.renginiuoaze.models.User;
import dev.zilvis.renginiuoaze.repository.TicketRepository;
import dev.zilvis.renginiuoaze.repository.UserRepository;
import dev.zilvis.renginiuoaze.security.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public void createTicketForUser(User user, Session session) {
        Tickets ticket = new Tickets();
        ticket.setEventId(session.getMetadata().get("eventId"));
        ticket.setUser(user);
        ticket.setCurrency(session.getCurrency());
        ticket.setPrice((double) (session.getAmountTotal() / 100));
        ticket.setActive(true);

        ticketRepository.save(ticket);
    }
}
