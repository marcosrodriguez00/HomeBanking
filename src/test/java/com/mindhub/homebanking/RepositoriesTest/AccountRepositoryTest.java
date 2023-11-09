package com.mindhub.homebanking.RepositoriesTest;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

// Las anotaciones @DataJpaTest y @AutoConfigureTestDatabase(replace = NONE) indican a Spring que
// debe escanear en busca de clases @Entity y configurar los repositorios JPA. Además hace que las
// operaciones realizadas en la base de datos sean por defecto transaccionales para que luego de ejecutarlas
// sean revertidas y no afecten los datos reales fuera de las pruebas, así como también indicar que se quiere
// conectar a una base de datos real y no a una embebida en la aplicación H2.
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    private List<Account> accounts;

    @BeforeEach
    public void getAccounts() {
        accounts = accountRepository.findAll();
    }

    @Test
    public void existsAccounts() {
        assertThat(accounts, is(not(empty())));
    }

    @Test
    public void accountStartsWithVIN() {

    }

    @Test
    public void accountsHavePositiveBalance() {
        assertThat(accounts, hasItem(hasProperty("balance", greaterThanOrEqualTo(0))));
    }

    //greaterThanOrEqualTo
    //assertThat(new BigDecimal("10.01"), greaterThanOrEqualTo(new BigDecimal("9.02")));

}
