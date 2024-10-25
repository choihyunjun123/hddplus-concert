package com.example.hddplusconcert.adapter.out.persistence;

import com.example.hddplusconcert.application.port.out.PaymentRepository;
import com.example.hddplusconcert.domain.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentRepositoryImpl implements PaymentRepository {

    private final SpringDataPaymentRepository repository;

    public PaymentRepositoryImpl(SpringDataPaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Payment payment) {
        PaymentEntity paymentEntity = PaymentEntity.fromDomainModel(payment);
        repository.save(paymentEntity);
    }
}
