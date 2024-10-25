package com.example.hddplusconcert.config;

import com.example.hddplusconcert.application.port.in.AuthUseCase;
import com.example.hddplusconcert.common.exception.CustomException;
import com.example.hddplusconcert.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthUseCase authUseCase;

    public AuthInterceptor(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String userId = request.getHeader("userId");
        authUseCase.validateAccessRights(token, userId);

        return true;
    }
}
