package com.biblioteca.gerenciador_biblioteca_api.controller;

import com.biblioteca.gerenciador_biblioteca_api.dto.MemberResponseDTO;
import com.biblioteca.gerenciador_biblioteca_api.model.Book;
import com.biblioteca.gerenciador_biblioteca_api.model.Member;
import com.biblioteca.gerenciador_biblioteca_api.service.MemberService;
import com.biblioteca.gerenciador_biblioteca_api.util.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ApiConstants.MEMBERS_URL)
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public Member saveMember(@RequestBody Member member) {
        return memberService.saveMember(member);
    }

    @GetMapping
    public ResponseEntity<Page<MemberResponseDTO>> findAllMembers(Pageable pageable){
        return ResponseEntity.ok().body(memberService.findAllMembers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> findMemberById(@PathVariable Long id) {
        MemberResponseDTO memberDTO = memberService.findMemberById(id);
        return ResponseEntity.ok().body(memberDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        Optional<Member> updateMemberOptional = memberService.updateMember(id, member);
        if(updateMemberOptional.isPresent()) {
            Member updatedMember = updateMemberOptional.get();
            return ResponseEntity.ok().body(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Member> deleteMember(@PathVariable Long id) {
        boolean deletedMember = memberService.deleteMember(id);
        if(deletedMember) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
