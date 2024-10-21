package com.example.hddplusconcert.adapter.out.persistence;

import com.example.hddplusconcert.domain.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    private String userId;

    private BigDecimal balance;

    public User toDomainModel() {
        return new User(userId, balance);
    }

    public static UserEntity fromDomainModel(User user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        return userEntity;
    }
}
