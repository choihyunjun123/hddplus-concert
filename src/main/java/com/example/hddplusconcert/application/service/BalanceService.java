package com.example.hddplusconcert.application.service;

import com.example.hddplusconcert.application.port.in.BalanceUseCase;
import com.example.hddplusconcert.application.port.out.UserRepository;
import com.example.hddplusconcert.common.exception.CustomException;
import com.example.hddplusconcert.common.exception.ErrorCode;
import com.example.hddplusconcert.domain.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceService implements BalanceUseCase {

    private final UserRepository userRepository;

    public BalanceService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void rechargeBalance(String userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.addBalance(amount);
        userRepository.save(user);
    }

    @Override
    public BigDecimal getBalance(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND))
                .getBalance();
    }
}
