package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.models.Account;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {

    Boolean existsAccountByNumber(String accountNumber);

    void saveAccount(Account account);

    List<Account> getAllAccounts();

    List<AccountDTO> getAllAccountDTO();

    Account getAccountById(Long id);

    AccountDTO getAccountDTOById(Long id);

    Account getAccountByNumber(String accountNumber);

    AccountDTO getAccountDTOByNumber(String accountNumber);

    void deleteAccountByNumber(String accountNumber);
}
