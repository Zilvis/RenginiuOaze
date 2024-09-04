package dev.zilvis.renginiuoaze.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import dev.zilvis.renginiuoaze.models.User;
import dev.zilvis.renginiuoaze.security.services.UserDetailsServiceImpl;
import dev.zilvis.renginiuoaze.services.MailService;
import dev.zilvis.renginiuoaze.services.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stripe")
public class StripeController {

    private final StripeService stripeService;
    private final UserDetailsServiceImpl userService;
    private final MailService mailService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody Map<String, Object> data) {
        try {
            String successUrl = data.get("successUrl").toString() + "?session_id={CHECKOUT_SESSION_ID}";
            String cancelUrl = data.get("cancelUrl").toString();
            Long amount = Long.parseLong(data.get("amount").toString());
            String currency = data.get("currency").toString();
            String email = data.get("customerEmail") != null ? data.get("customerEmail").toString() : "";
            String eventId = data.get("eventId").toString();

            Session session;
            if (email.isEmpty()) {
                session = stripeService.createCheckoutSession(successUrl, cancelUrl, amount, currency, null, eventId);
            } else {
                session = stripeService.createCheckoutSession(successUrl, cancelUrl, amount, currency, email, eventId);
            }

            Map<String, String> responseData = new HashMap<>();
            responseData.put("id", session.getId());

            return ResponseEntity.ok(responseData);
        } catch (StripeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


    @GetMapping("/payment-success")
    public String paymentSuccess(@RequestParam("session_id") String sessionId) {
        try {
            Session session = Session.retrieve(sessionId);
            String customerEmail = session.getCustomerDetails().getEmail();

            if (customerEmail == null) {
                throw new IllegalStateException("Customer email is missing in the Stripe session.");
            }

            User user = userService.findOrCreateUserByEmail(customerEmail);

            userService.createTicketForUser(user, session);

            mailService.sendHtmlEmail("Testas subject",customerEmail,"Labas");

            return "redirect:/success.html";
        } catch (StripeException e) {
            e.printStackTrace();
            return "redirect:/error.html";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error.html";
        }
    }
}

