package com.mindhub.homebanking.RepositoriesTest;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LoanRepositoryTest {

    @Autowired
    LoanRepository loanRepository;
    private List<Loan> loans;

    @BeforeEach
    public void getLoans() {
        loans = loanRepository.findAll();
    }

    @Test
    public void existsLoans() {
        assertThat(loans, is(not(empty())));
    }

    @Test
    public void existsPersonalLoan () {
        assertThat(loans, hasItem(hasProperty("name", equalTo("Personal"))));
    }

    @Test
    public void existsCarLoan () {
        assertThat(loans, hasItem(hasProperty("name", equalTo("Car"))));
    }

    @Test
    public void existsMortgageLoan () {
        assertThat(loans, hasItem(hasProperty("name", equalTo("Mortgage"))));
    }
}
