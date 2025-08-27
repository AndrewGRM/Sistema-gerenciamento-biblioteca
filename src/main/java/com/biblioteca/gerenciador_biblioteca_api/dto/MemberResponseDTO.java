package com.biblioteca.gerenciador_biblioteca_api.dto;

public class MemberResponseDTO {

    Long id;
    String name;
    String email;
    String phoneNumber;

    public MemberResponseDTO(Long id, String nome, String email, String telefone) {
        this.id = id;
        this.name = nome;
        this.email = email;
        this.phoneNumber = telefone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
