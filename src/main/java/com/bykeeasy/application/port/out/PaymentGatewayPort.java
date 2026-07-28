package com.bykeeasy.application.port.out;

import java.math.BigDecimal;

public interface PaymentGatewayPort {
    boolean processPayment(String walletId, BigDecimal amount, String paymentMethodId);
}
