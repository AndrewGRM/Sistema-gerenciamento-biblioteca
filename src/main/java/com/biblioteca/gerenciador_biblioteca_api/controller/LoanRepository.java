package com.biblioteca.gerenciador_biblioteca_api.controller;

import com.biblioteca.gerenciador_biblioteca_api.model.Loan;
import org.springframework.data.repository.Repository;

interface LoanRepository extends Repository<Loan, Long> {
}
