package com.biblioteca.gerenciador_biblioteca_api.repository;


import com.biblioteca.gerenciador_biblioteca_api.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findByBookIdAndReturnDateIsNull(Long bookId);

    Page<Loan> findByReturnDateIsNull(Pageable pageable);

    Page<Loan> findByMemberId(Long memberId, Pageable pageable);

    Page<Loan> findByBookId(Long bookId, Pageable pageable);

    Page<Loan> findAll(Pageable pageable);

}
