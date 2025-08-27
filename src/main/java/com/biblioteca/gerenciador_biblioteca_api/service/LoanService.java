package com.biblioteca.gerenciador_biblioteca_api.service;

import com.biblioteca.gerenciador_biblioteca_api.dto.LoanResponseDTO;
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
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<Member> member = memberRepository.findById(memberId);
        if(book.isPresent() && member.isPresent() && !loanRepository.findByBookIdAndReturnDateIsNull(bookId).isPresent()) {
            Book foundBook = book.get();
            Member foundMember = member.get();

            Loan loan = new Loan();
            loan.setBook(foundBook);
            loan.setMember(foundMember);
            loan.setLoanDate(LocalDate.now());

            return loanRepository.save(loan);
        } else {
            throw new IllegalArgumentException("Livro ou membro não encontrado, ou livro já emprestado.");
        }
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
            throw new IllegalArgumentException("Membro não encontrado com o ID: " + memberId);
        }
    }

    public Page<LoanResponseDTO> findLoansByBookId(Long bookId, Pageable pageable){
        if(bookRepository.existsById(bookId)) {
            Page<Loan> loanPage = loanRepository.findByBookId(bookId, pageable);
            return loanPage.map(LoanMapper::toDTO);
        } else {
            throw new IllegalArgumentException("Livro não encontrado com o ID: " + bookId);
        }
    }
}