package com.biblioteca.gerenciador_biblioteca_api.service;

import com.biblioteca.gerenciador_biblioteca_api.dto.LoanResponseDTO;
import com.biblioteca.gerenciador_biblioteca_api.exception.BookAlreadyLoanedException;
import com.biblioteca.gerenciador_biblioteca_api.exception.ResourceNotFoundException;
import com.biblioteca.gerenciador_biblioteca_api.model.Book;
import com.biblioteca.gerenciador_biblioteca_api.model.Loan;
import com.biblioteca.gerenciador_biblioteca_api.model.Member;
import com.biblioteca.gerenciador_biblioteca_api.repository.BookRepository;
import com.biblioteca.gerenciador_biblioteca_api.repository.LoanRepository;
import com.biblioteca.gerenciador_biblioteca_api.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Loan saveNewLoan(Long bookId, Long memberId) {
        Book foundBook = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com o ID: " + bookId));

        Member foundMember = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("Membro não encontrado com o ID: " + memberId));

        if(loanRepository.findByBookIdAndReturnDateIsNull(bookId).isPresent()) {
            throw new BookAlreadyLoanedException("O livro já está emprestado.");
        }

        Loan loan = new Loan();
        loan.setBook(foundBook);
        loan.setMember(foundMember);
        loan.setLoanDate(LocalDate.now());

        return loanRepository.save(loan);
    }

    public Optional<Loan> returnBook(Long id) {
        Optional<Loan> loanOptional = loanRepository.findById(id);
        if(loanOptional.isPresent() && loanOptional.get().getReturnDate() == null ) {
            Loan loan = loanOptional.get();
            loan.setReturnDate(LocalDate.now());

            return Optional.of(loanRepository.save(loan));
        } else {
            return Optional.empty();
        }
    }

    public Page<LoanResponseDTO> findAllLoans(Pageable pageable) {
        Page<Loan> loanPage = loanRepository.findAll(pageable);

        return loanPage.map(LoanMapper::toDTO);
    }

    public Page<LoanResponseDTO> findActiveLoans(Pageable pageable){
        Page<Loan> loanPage = loanRepository.findByReturnDateIsNull(pageable);
        return loanPage.map(LoanMapper::toDTO);
    }

    public Page<LoanResponseDTO> findLoansByMemberId(Long memberId, Pageable pageable){
        if(memberRepository.existsById(memberId)) {
            Page<Loan> loanPage = loanRepository.findByMemberId(memberId, pageable);
            return loanPage.map(LoanMapper::toDTO);
        } else {
            throw new ResourceNotFoundException("Membro não encontrado com o ID: " + memberId);
        }
    }

    public Page<LoanResponseDTO> findLoansByBookId(Long bookId, Pageable pageable){
        if(bookRepository.existsById(bookId)) {
            Page<Loan> loanPage = loanRepository.findByBookId(bookId, pageable);
            return loanPage.map(LoanMapper::toDTO);
        } else {
            throw new ResourceNotFoundException("Livro não encontrado com o ID: " + bookId);
        }
    }
}