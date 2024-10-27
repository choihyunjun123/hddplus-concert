package com.example.hddplusconcert.application.service;

import com.example.hddplusconcert.application.port.in.PaymentUseCase;
import com.example.hddplusconcert.application.port.out.PaymentRepository;
import com.example.hddplusconcert.application.port.out.SeatRepository;
import com.example.hddplusconcert.application.port.out.UserRepository;
import com.example.hddplusconcert.common.exception.CustomException;
import com.example.hddplusconcert.common.exception.ErrorCode;
import com.example.hddplusconcert.domain.model.Payment;
import com.example.hddplusconcert.domain.model.Seat;
import com.example.hddplusconcert.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Payment makePayment(String userId, Long seatNumber, Long concertId, BigDecimal amount) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Seat seat = seatRepository.findBySeatNumberAndConcertId(seatNumber, concertId)
                .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));

        if (!seat.isHeldBy(userId)) {
            throw new CustomException(ErrorCode.SEAT_NOT_HELD_BY_USER);
        }

        user.deductBalance(amount);
        seat.reserve(userId);

        userRepository.save(user);
        seatRepository.save(seat);

        Payment payment = new Payment(userId, seatNumber, concertId, amount);
        paymentRepository.save(payment);

        return payment;
    }
}
