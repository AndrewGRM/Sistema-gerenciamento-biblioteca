package com.biblioteca.gerenciador_biblioteca_api.controller.auth;

import com.biblioteca.gerenciador_biblioteca_api.dto.LoginRequestDTO;
import com.biblioteca.gerenciador_biblioteca_api.model.Member;
import com.biblioteca.gerenciador_biblioteca_api.service.auth.TokenService;
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
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> Login(@RequestBody LoginRequestDTO loginRequestDTO) {
        // 1. Cria o objeto de autenticação com as credenciais do DTO
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        // 2. Autentica o usuário e lança exceção se as credenciais forem inválidas
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        // 3. (Nova) Gere o token com base no usuário autenticado
        String token = tokenService.generateToken((Member) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }
}
