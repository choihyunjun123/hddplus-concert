package com.example.hddplusconcert.adapter.in.web;

import com.example.hddplusconcert.application.port.in.UserUseCase;
import com.example.hddplusconcert.common.dto.user.UserRequest;
import com.example.hddplusconcert.common.dto.user.UserResponse;
import com.example.hddplusconcert.domain.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {
        User user = userUseCase.register(userRequest.userId());
        UserResponse response = UserResponse.fromDomainModel(user);
        return ResponseEntity.ok(response);
    }

    // 회원 찾기
    @PostMapping("/find")
    public ResponseEntity<UserResponse> find(@RequestBody UserRequest userRequest) {
        User user = userUseCase.findByUserId(userRequest.userId());
        UserResponse response = UserResponse.fromDomainModel(user);
        return ResponseEntity.ok(response);
    }

    // 모든 회원 찾기
    @GetMapping("/find-all")
    public ResponseEntity<List<UserResponse>> findAll() {
        List<User> users = userUseCase.findAll();
        List<UserResponse> responses = users.stream()
                .map(UserResponse::fromDomainModel)
                .toList();
        return ResponseEntity.ok(responses);
    }
}
