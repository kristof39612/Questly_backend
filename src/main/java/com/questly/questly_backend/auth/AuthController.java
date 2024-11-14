package com.questly.questly_backend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerClient(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.registerUser(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginClient(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.loginUserEmail(request));
    }
}
