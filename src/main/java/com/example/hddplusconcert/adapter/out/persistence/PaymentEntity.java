package com.example.hddplusconcert.adapter.out.persistence;

import com.example.hddplusconcert.domain.model.Payment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private String userId;
    private Long seatNumber;
    private String concertId;
    private BigDecimal amount;
    private LocalDateTime paymentTime;

    public Payment toDomainModel() {
        Payment payment = new Payment();
        BeanUtils.copyProperties(this, payment);
        return payment;
    }

    public static PaymentEntity fromDomainModel(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        BeanUtils.copyProperties(payment, entity);
        return entity;
    }
}
