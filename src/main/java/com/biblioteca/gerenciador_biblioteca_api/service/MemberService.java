package com.biblioteca.gerenciador_biblioteca_api.service;

import com.biblioteca.gerenciador_biblioteca_api.dto.MemberResponseDTO;
import com.biblioteca.gerenciador_biblioteca_api.exception.ResourceNotFoundException;
import com.biblioteca.gerenciador_biblioteca_api.model.Member;
import com.biblioteca.gerenciador_biblioteca_api.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public Page<MemberResponseDTO> findAllMembers(Pageable pageable) {
        Page<Member> memberPage = memberRepository.findAll(pageable);
        return memberPage.map(MemberMapper::toDTO);
    }

    public MemberResponseDTO findMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membro não encontrado com o ID: " + id));
        return MemberMapper.toDTO(member);
    }

    public Optional<Member> updateMember(Long id, Member member) {
        Optional<Member> memberById = memberRepository.findById(id);
        if (memberById.isPresent()) {
            Member existingMember = memberById.get();
            existingMember.setName(member.getName());
            existingMember.setEmail(member.getEmail());
            existingMember.setPhoneNumber(member.getPhoneNumber());
            memberRepository.save(existingMember);
            return Optional.of(memberRepository.save(existingMember));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteMember(Long id) {
        boolean memberExists = memberRepository.existsById(id);
        if(memberExists) {
            memberRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
