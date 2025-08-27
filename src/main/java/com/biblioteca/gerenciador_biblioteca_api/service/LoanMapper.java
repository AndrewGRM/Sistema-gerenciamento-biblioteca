package com.biblioteca.gerenciador_biblioteca_api.service;

import com.biblioteca.gerenciador_biblioteca_api.dto.LoanResponseDTO;
import com.biblioteca.gerenciador_biblioteca_api.model.Loan;

public class LoanMapper {

    public static LoanResponseDTO toDTO(Loan loan){
        return new LoanResponseDTO(loan);
    }
}
