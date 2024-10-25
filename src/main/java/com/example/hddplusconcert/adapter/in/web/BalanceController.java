package com.example.hddplusconcert.adapter.in.web;

import com.example.hddplusconcert.application.port.in.BalanceUseCase;
import com.example.hddplusconcert.common.dto.balance.BalanceRequest;
import com.example.hddplusconcert.common.dto.user.UserResponse;
import com.example.hddplusconcert.domain.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final BalanceUseCase balanceUseCase;

    public BalanceController(BalanceUseCase balanceUseCase) {
        this.balanceUseCase = balanceUseCase;
    }

    @PostMapping("/charge")
    public ResponseEntity<UserResponse> chargeBalance(
            @RequestHeader("userId") String userId,
            @RequestBody BalanceRequest balanceRequest) {
        User user = balanceUseCase.chargeBalance(userId, balanceRequest.amount());
        UserResponse response = UserResponse.fromDomainModel(user);
        return ResponseEntity.ok(response);
    }
}
