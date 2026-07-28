package com.bykeeasy.infrastructure.adapter.in.web;

import com.bykeeasy.domain.model.Wallet;
import com.bykeeasy.domain.model.Transaction;
import com.bykeeasy.application.port.in.WalletUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletRestController {

    private final WalletUseCase walletUseCase;

    public WalletRestController(WalletUseCase walletUseCase) {
        this.walletUseCase = walletUseCase;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Wallet> getWallet(@PathVariable String userId) {
        return ResponseEntity.ok(walletUseCase.getWalletByUserId(userId));
    }

    @PostMapping("/user/{userId}/topup")
    public ResponseEntity<Void> topUp(
            @PathVariable String userId,
            @RequestParam BigDecimal amount,
            @RequestParam String paymentMethodId) {
        walletUseCase.topUp(userId, amount, paymentMethodId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String userId) {
        return ResponseEntity.ok(walletUseCase.getTransactionsByUserId(userId));
    }
}
