package com.example.hddplusconcert.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 인증 응답에 사용
@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String token;
}
