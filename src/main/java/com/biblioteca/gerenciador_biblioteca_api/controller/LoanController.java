package com.biblioteca.gerenciador_biblioteca_api.controller;

import com.biblioteca.gerenciador_biblioteca_api.dto.LoanRequest;
import com.biblioteca.gerenciador_biblioteca_api.model.Loan;
import com.biblioteca.gerenciador_biblioteca_api.service.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
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
@RequestMapping("/api/loans")
public class LoanController {

    private LoanService loanService;

    @PostMapping("/borrow")
    public ResponseEntity<Loan> borrowBook(@RequestBody LoanRequest loanRequest){
        Loan newLoan = loanService.saveNewLoan(loanRequest.getBookId(), loanRequest.getMemberId());
        if(newLoan == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(newLoan);
        }
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Loan> returnBook(@PathVariable Long id) {
        Optional<Loan> returnLoan = loanService.returnBook(id);
        if(returnLoan.isPresent()) {
            return ResponseEntity.ok().body(returnLoan.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping()
    public ResponseEntity<Page<Loan>> findAllLoans(Pageable pageable) {
        return ResponseEntity.ok().body(loanService.findAllLoans(pageable));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Loan>> findActiveLoans(){
        return ResponseEntity.ok().body(loanService.findActiveLoans());
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Loan>> findLoansByMemberId(@PathVariable Long memberId){
        return ResponseEntity.ok().body(loanService.findLoansByMemberId(memberId));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Loan>> findLoansByBookId(@PathVariable Long bookId){
        return ResponseEntity.ok().body(loanService.findLoansByBookId(bookId));
    }
}
