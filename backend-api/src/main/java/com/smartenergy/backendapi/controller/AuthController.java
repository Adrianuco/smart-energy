package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.dto.LoginRequest;
import com.smartenergy.backendapi.dto.LoginResponse;
import com.smartenergy.backendapi.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {

        return ResponseEntity.ok(
                service.login(
                        request.getCif(),
                        request.getPassword()
                )
        );
    }
}
