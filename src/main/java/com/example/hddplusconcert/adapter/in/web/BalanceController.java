package com.example.hddplusconcert.adapter.in.web;

import com.example.hddplusconcert.application.port.in.BalanceUseCase;
import com.example.hddplusconcert.common.dto.BalanceRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final BalanceUseCase balanceUseCase;

    public BalanceController(BalanceUseCase balanceUseCase) {
        this.balanceUseCase = balanceUseCase;
    }

    @PostMapping("/recharge")
    public ResponseEntity<?> rechargeBalance(@RequestBody BalanceRequest balanceRequest) {
        balanceUseCase.rechargeBalance(balanceRequest.getUserId(), balanceRequest.getAmount());
        return ResponseEntity.ok("Balance recharged successfully.");
    }

    @GetMapping
    public ResponseEntity<?> getBalance(@RequestParam String userId) {
        BigDecimal balance = balanceUseCase.getBalance(userId);
        return ResponseEntity.ok("Your balance: " + balance);
    }
}
