package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions();

    List<TransactionDTO> getAllTransactionDTO();

    Transaction getTransaction(Long id);

    TransactionDTO getTransactionDTO(Long id);

    void saveTransaction(Transaction transaction);
}
