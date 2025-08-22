package com.biblioteca.gerenciador_biblioteca_api.service;

import com.biblioteca.gerenciador_biblioteca_api.exception.GlobalExceptionHandler;
import com.biblioteca.gerenciador_biblioteca_api.model.Book;
import com.biblioteca.gerenciador_biblioteca_api.model.Loan;
import com.biblioteca.gerenciador_biblioteca_api.model.Member;
import com.biblioteca.gerenciador_biblioteca_api.repository.BookRepository;
import com.biblioteca.gerenciador_biblioteca_api.repository.LoanRepository;
import com.biblioteca.gerenciador_biblioteca_api.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private LoanRepository loanRepository;

    private BookRepository bookRepository;

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

    public Page<Loan> findAllLoans(Pageable pageable) {
        return loanRepository.findAll(pageable);
    }

    public List<Loan> findActiveLoans(){
        return loanRepository.findByReturnDateIsNull();
    }

    public List<Loan> findLoansByMemberId(Long memberId){
        if(memberRepository.existsById(memberId)) {
            return loanRepository.findByMemberId(memberId);
        } else {
            throw new IllegalArgumentException("Membro não encontrado com o ID: " + memberId);
        }
    }

    public List<Loan> findLoansByBookId(Long bookId){
        if(bookRepository.existsById(bookId)) {
            return loanRepository.findByBookId(bookId);
        } else {
            throw new IllegalArgumentException("Livro não encontrado com o ID: " + bookId);
        }
    }
}