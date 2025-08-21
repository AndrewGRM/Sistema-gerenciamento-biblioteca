package com.biblioteca.gerenciador_biblioteca_api.service;

import com.biblioteca.gerenciador_biblioteca_api.model.Book;
import com.biblioteca.gerenciador_biblioteca_api.model.Member;
import com.biblioteca.gerenciador_biblioteca_api.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> findAllMembers() {
        List<Member> allMembers = memberRepository.findAll();
        return allMembers;
    }

    public Optional<Member> findMemberById(Long id) {
        Optional<Member> memberById = memberRepository.findById(id);
        return memberById;
    }

    public Optional<Member> updateMember(Long id, Member member) {
        Optional<Member> memberById = memberRepository.findById(id);
        if (memberById.isPresent()) {
            Member existingMember = memberById.get();
            existingMember.setNome(member.getNome());
            existingMember.setEmail(member.getEmail());
            existingMember.setTelefone(member.getTelefone());
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
