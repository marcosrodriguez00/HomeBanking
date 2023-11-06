package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanImplement {

    @Autowired
    LoanRepository loanRepository;
}
