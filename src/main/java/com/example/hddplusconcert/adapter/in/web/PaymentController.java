package com.example.hddplusconcert.adapter.in.web;

import com.example.hddplusconcert.application.port.in.PaymentUseCase;
import com.example.hddplusconcert.common.dto.PaymentRequest;
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
    public ResponseEntity<String> makePayment(@RequestBody PaymentRequest paymentRequest) {
        boolean success = paymentUseCase.makePayment(
                paymentRequest.getUserId(),
                paymentRequest.getSeatNumber(),
                paymentRequest.getConcertId(),
                paymentRequest.getAmount()
        );
        if (success) {
            return ResponseEntity.ok("Payment Successful.");
        } else {
            return ResponseEntity.status(400).body("Payment Failed.");
        }
    }
}
