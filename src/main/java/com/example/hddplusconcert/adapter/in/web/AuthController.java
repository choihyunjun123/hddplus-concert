package com.example.hddplusconcert.adapter.in.web;

import com.example.hddplusconcert.application.port.in.AuthUseCase;
import com.example.hddplusconcert.common.dto.auth.AuthResponse;
import com.example.hddplusconcert.common.dto.auth.QueuePositionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    // 토큰 발급 API
    @PostMapping("/token")
    public ResponseEntity<AuthResponse> getToken(
            @RequestHeader("userId") String userId
    ) {
        String token = authUseCase.generateToken(userId);
        AuthResponse response = AuthResponse.fromDomainModel(token);
        return ResponseEntity.ok(response);
    }

    // 대기열 위치 조회
    @GetMapping("/queue")
    public ResponseEntity<?> getQueuePosition(
            @RequestHeader("userId") String userId
    ) {
        Long position = authUseCase.getQueuePosition(userId);
        if (position == -1L) {
            return ResponseEntity.ok("Not in queue position");
        }
        QueuePositionResponse response = QueuePositionResponse.fromDomainModel(userId, position);
        return ResponseEntity.ok(response);
    }
}
