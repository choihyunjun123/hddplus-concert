package com.example.hddplusconcert.adapter.in.web;

import com.example.hddplusconcert.application.port.in.AuthUseCase;
import com.example.hddplusconcert.common.dto.AuthRequest;
import com.example.hddplusconcert.common.dto.AuthResponse;
import com.example.hddplusconcert.common.dto.user.UserRequest;
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
    public ResponseEntity<AuthResponse> getToken(@RequestBody AuthRequest authRequest) {
        String token = authUseCase.generateToken(authRequest.getUserId());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    // 대기열 위치 조회
    @GetMapping("/queue")
    public ResponseEntity<?> getQueuePosition(@RequestParam String userId) {
        Long position = authUseCase.getQueuePosition(userId);
        return ResponseEntity.ok("Your position is: " + position);
    }
}
