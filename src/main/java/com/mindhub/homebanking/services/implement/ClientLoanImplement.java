package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanImplement implements ClientLoanService {

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Override
    public void saveClientLoan( ClientLoan clientLoan ) {
        clientLoanRepository.save( clientLoan );
    }

    @Override
    public ClientLoan getById( long id ) {
        return clientLoanRepository.findById( id );
    }

    @Override
    public boolean existsById( long id ) {
        return clientLoanRepository.existsById( id );
    }

    @Override
    public boolean existsClientLoanByLoanAndClientAndIsActive(Loan loan, Client client, boolean isActive) {
        return clientLoanRepository.existsByLoanAndClientAndIsActive(loan, client, isActive);
    }
}
