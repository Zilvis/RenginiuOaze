package dev.zilvis.renginiuoaze.services;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

public interface StripeService {
    Session createCheckoutSession(String successUrl, String cancelUrl, Long amount, String currency, String customerEmail, String eventId) throws StripeException;
}
