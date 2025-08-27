package com.biblioteca.gerenciador_biblioteca_api.service;

import com.biblioteca.gerenciador_biblioteca_api.dto.MemberResponseDTO;
import com.biblioteca.gerenciador_biblioteca_api.model.Member;

public class MemberMapper {

    public static MemberResponseDTO toDTO(Member member) {
        return new MemberResponseDTO(
                member.getId(),
                member.getNome(),
                member.getEmail(),
                member.getTelefone()
        );
    }
}
