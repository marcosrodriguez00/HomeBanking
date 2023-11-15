package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    List<Loan> getAllLoans();

    List<LoanDTO> getAllLoanDTO();

    Loan getLoanById(Long id);

    LoanDTO getLoanDTOById(Long id);

    void saveLoan(Loan loan);

    boolean existsLoanByName(String name);

    void deleteLoanById(long id);

    boolean existsLoanById(long id);
}
