package com.example.hddplusconcert.application.service;

import com.example.hddplusconcert.application.port.in.PaymentUseCase;
import com.example.hddplusconcert.application.port.out.PaymentRepository;
import com.example.hddplusconcert.application.port.out.SeatRepository;
import com.example.hddplusconcert.application.port.out.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService implements PaymentUseCase {

    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final PaymentRepository paymentRepository;


    public PaymentService(UserRepository userRepository,
                          SeatRepository seatRepository,
                          PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.seatRepository = seatRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public boolean makePayment(String userId, Long seatNumber, String concertId, BigDecimal amount) {
        return false;
    }
}
