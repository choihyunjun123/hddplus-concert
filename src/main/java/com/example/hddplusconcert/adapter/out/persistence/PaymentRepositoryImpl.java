package com.example.hddplusconcert.adapter.out.persistence;

import com.example.hddplusconcert.application.port.out.PaymentRepository;
import com.example.hddplusconcert.domain.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentRepositoryImpl implements PaymentRepository {

    @Override
    public void save(Payment payment) {

    }
}
