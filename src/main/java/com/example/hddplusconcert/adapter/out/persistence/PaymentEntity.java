package com.example.hddplusconcert.adapter.out.persistence;

import com.example.hddplusconcert.domain.model.Payment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(nullable = false)
    private String userId;

    @NotNull
    @Column(nullable = false)
    private Long seatNumber;

    @NotNull
    @Column(nullable = false)
    private String concertId;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(nullable = false)
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
