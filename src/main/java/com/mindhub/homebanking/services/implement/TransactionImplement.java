package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionImplement {

    @Autowired
    TransactionRepository transactionRepository;
}
