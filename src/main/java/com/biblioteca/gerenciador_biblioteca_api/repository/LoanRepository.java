package com.biblioteca.gerenciador_biblioteca_api.repository;


import com.biblioteca.gerenciador_biblioteca_api.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findByBookIdAndReturnDateIsNull(Long bookId);

    List<Loan> findByReturnDateIsNull();

    List<Loan> findByMemberId(Long memberId);

    List<Loan> findByBookId(Long bookId);

    Page<Loan> findAll(Pageable pageable);

}
