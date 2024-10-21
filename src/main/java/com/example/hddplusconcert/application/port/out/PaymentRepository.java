package com.example.hddplusconcert.application.port.out;

import com.example.hddplusconcert.domain.model.Payment;

public interface PaymentRepository {
    void save(Payment payment);
}
