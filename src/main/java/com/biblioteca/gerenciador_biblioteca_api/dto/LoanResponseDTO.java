package com.biblioteca.gerenciador_biblioteca_api.dto;


import com.biblioteca.gerenciador_biblioteca_api.model.Loan;

import java.time.LocalDate;

public class LoanResponseDTO {

    Long id;
    String bookTitle;
    String memberName;
    LocalDate loanDate;
    LocalDate returnDate;

    public LoanResponseDTO(Loan loan) {
        this.id = loan.getId();
        this.bookTitle = loan.getBook().getTitle();
        this.memberName = loan.getMember().getNome();
        this.loanDate = loan.getLoanDate();
        this.returnDate = loan.getReturnDate();
    }

    public Long getId() {
        return id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getMemberName() {
        return memberName;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}
