package com.bykeeasy.infrastructure.adapter.out.payment;

import com.bykeeasy.application.port.out.PaymentGatewayPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

public class MockPaymentAdapter implements PaymentGatewayPort {
    @Override
    public boolean processPayment(String walletId, BigDecimal amount, String paymentMethodId) {
        // Mock successful payment
        System.out.println("Processing mock payment of " + amount + " for wallet " + walletId);
        return true;
    }
}
