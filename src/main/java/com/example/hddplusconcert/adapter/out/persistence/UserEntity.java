package com.example.hddplusconcert.adapter.out.persistence;

import com.example.hddplusconcert.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
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

    @Column(nullable = true)
    private String userId;

    @Min(0)
    private BigDecimal balance;

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
