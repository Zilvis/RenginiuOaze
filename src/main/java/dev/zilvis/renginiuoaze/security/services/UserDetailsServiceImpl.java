package dev.zilvis.renginiuoaze.security.services;

import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import dev.zilvis.renginiuoaze.models.Tickets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.zilvis.renginiuoaze.models.User;
import dev.zilvis.renginiuoaze.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public void createTicketForUser(User user, Session session) {
        Tickets ticket = new Tickets();
        ticket.setEventId(session.getMetadata().get("eventId"));
        ticket.setUser(user);
        ticket.setCurrency(session.getCurrency());
        ticket.setPrice((double) (session.getAmountTotal() / 100));
        user.getTickets().add(ticket);
        userRepository.save(user);
    }

    public User findOrCreateUserByEmail(String email) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setPassword("null");
            user.setUsername("null");// TODO
        }
        return user;
    }
}
