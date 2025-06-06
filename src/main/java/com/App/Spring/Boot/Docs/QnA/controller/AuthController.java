package com.App.Spring.Boot.Docs.QnA.controller;

import com.App.Spring.Boot.Docs.QnA.dto.LoginRequest;
import com.App.Spring.Boot.Docs.QnA.dto.LoginResponse;
import com.App.Spring.Boot.Docs.QnA.dto.RegisterRequest;
import com.App.Spring.Boot.Docs.QnA.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        userService.register(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(userService.login(req));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // JWT-based - client side token deletion
        return ResponseEntity.ok().build();
    }
}
