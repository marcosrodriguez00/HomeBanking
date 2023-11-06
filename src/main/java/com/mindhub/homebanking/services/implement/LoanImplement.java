package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LoanImplement implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<LoanDTO> getAllLoanDTO() {
        return getAllLoans().stream().map(LoanDTO::new).collect(toList());
    }

    @Override
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public LoanDTO getLoanDTOById(Long id) {
        return new LoanDTO(getLoanById(id));
    }

    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }
}
