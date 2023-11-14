package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dto.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionImplement implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<TransactionDTO> getAllTransactionDTO() {
        return getAllTransactions().stream().map(TransactionDTO::new).collect(Collectors.toList());
    }

    @Override
    public Transaction getTransaction(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public TransactionDTO getTransactionDTO(Long id) {
        return new TransactionDTO(getTransaction(id));
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransactions(String accountNumber) {
       Set<Transaction> transactions = accountRepository.findByNumber(accountNumber).getTransactions();
        transactions.forEach(transaction -> {
            transaction.setActive(false);
            saveTransaction(transaction);
        });
    }
}
