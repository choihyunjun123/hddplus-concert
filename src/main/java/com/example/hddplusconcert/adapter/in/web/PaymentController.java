package com.example.hddplusconcert.adapter.in.web;

import com.example.hddplusconcert.application.port.in.PaymentUseCase;
import com.example.hddplusconcert.common.dto.payment.PaymentRequest;
import com.example.hddplusconcert.common.dto.payment.PaymentResponse;
import com.example.hddplusconcert.domain.model.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    public PaymentController(PaymentUseCase paymentUseCase) {
        this.paymentUseCase = paymentUseCase;
    }

    // 결제 처리
    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> makePayment(
            @RequestHeader("userId") String userId,
            @RequestBody PaymentRequest paymentRequest) {
        Payment payment = paymentUseCase.makePayment(
                userId,
                paymentRequest.seatNumber(),
                paymentRequest.concertId(),
                paymentRequest.amount()
        );
        PaymentResponse response = PaymentResponse.fromDomainModel(payment);
        return ResponseEntity.ok(response);
    }
}
