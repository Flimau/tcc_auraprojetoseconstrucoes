package com.tccfer.application.controller;

import com.tccfer.application.controller.dto.auth.JwtResponse;
import com.tccfer.application.controller.dto.auth.UsuarioLoginDTO;
import com.tccfer.application.model.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioLoginDTO request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getSenha())
        );

        String token = jwtService.generateToken(auth);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
