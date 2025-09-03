package com.biblioteca.gerenciador_biblioteca_api.controller;

import com.biblioteca.gerenciador_biblioteca_api.dto.LoanRequest;
import com.biblioteca.gerenciador_biblioteca_api.dto.LoanResponseDTO;
import com.biblioteca.gerenciador_biblioteca_api.model.Loan;
import com.biblioteca.gerenciador_biblioteca_api.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
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
    public ResponseEntity<Page<LoanResponseDTO>> findAllLoans(Pageable pageable) {
        return ResponseEntity.ok().body(loanService.findAllLoans(pageable));
    }

    @GetMapping("/active")
    public ResponseEntity<Page<LoanResponseDTO>> findActiveLoans(Pageable pageable){
        return ResponseEntity.ok().body(loanService.findActiveLoans(pageable));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<Page<LoanResponseDTO>> findLoansByMemberId(@PathVariable Long memberId, Pageable pageable){
        return ResponseEntity.ok().body(loanService.findLoansByMemberId(memberId, pageable));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<Page<LoanResponseDTO>> findLoansByBookId(@PathVariable Long bookId, Pageable pageable){
        return ResponseEntity.ok().body(loanService.findLoansByBookId(bookId, pageable));
    }
}
