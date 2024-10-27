package com.example.hddplusconcert.adapter.out.persistence;

import com.example.hddplusconcert.domain.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    @NotNull
    @NotBlank
    private String userId;

    @Column(nullable = false)
    @Min(0)
    private BigDecimal balance;

    @Version
    private Long version; // 낙관적 락을 위한 버전 필드

    public User toDomainModel() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }

    public static UserEntity fromDomainModel(User user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        return userEntity;
    }
}
