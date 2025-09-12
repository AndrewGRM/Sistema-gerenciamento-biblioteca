package com.biblioteca.gerenciador_biblioteca_api.controller.auth;

import com.biblioteca.gerenciador_biblioteca_api.dto.LoginRequestDTO;
import com.biblioteca.gerenciador_biblioteca_api.dto.LoginResponseDTO;
import com.biblioteca.gerenciador_biblioteca_api.dto.RegisterRequestDTO;
import com.biblioteca.gerenciador_biblioteca_api.model.Member;
import com.biblioteca.gerenciador_biblioteca_api.repository.MemberRepository;
import com.biblioteca.gerenciador_biblioteca_api.service.auth.TokenService;
import com.biblioteca.gerenciador_biblioteca_api.util.ApiConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.AUTH_URL)
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final TokenService tokenService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService,
                          MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> Login(@RequestBody LoginRequestDTO loginRequestDTO) {
        // 1. Cria o objeto de autenticação com as credenciais do DTO
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        // 2. Autentica o usuário e lança exceção se as credenciais forem inválidas
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        // 3. (Nova) Gere o token com base no usuário autenticado
        String token = tokenService.generateToken((Member) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        if(memberRepository.findByEmail(registerRequestDTO.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body("Email already registered.");
        }

        Member newMember = new Member();
        newMember.setEmail(registerRequestDTO.getEmail());
        newMember.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        newMember.setName(registerRequestDTO.getName());
        newMember.setPhoneNumber(registerRequestDTO.getPhoneNumber());

        memberRepository.save(newMember);

        return ResponseEntity.ok("Registration successful.");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok().build();
    }
}
