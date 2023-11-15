package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public interface ClientLoanService {

    void saveClientLoan(ClientLoan clientLoan);

    ClientLoan getById(long id);

    boolean existsById(long id);

    boolean existsClientLoanByLoanAndClientAndIsActive(Loan loan, Client client, boolean isActive);
}
