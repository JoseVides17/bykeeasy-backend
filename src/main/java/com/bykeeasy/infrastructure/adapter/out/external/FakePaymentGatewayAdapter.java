package com.bykeeasy.infrastructure.adapter.out.external;

import com.bykeeasy.application.port.out.PaymentGatewayPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FakePaymentGatewayAdapter implements PaymentGatewayPort {
    @Override
    public boolean processPayment(String walletId, BigDecimal amount, String paymentMethodId) {
        // In a real scenario, this would call Stripe, PayPal, or MercadoPago API
        System.out.println("[PAYMENT] Processing payment for wallet " + walletId + " of amount " + amount);
        return true;
    }
}
