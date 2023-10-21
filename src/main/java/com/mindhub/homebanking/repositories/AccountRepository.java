package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// <Account, Long> se coloca para indicar que la interfaz accountRepository haga operaciones CRUD en la entidad Account
// indica que este repositorio trabaja bajo los lineamientos de REST, para repesentar los datos en formato JSON o XML
@RepositoryRestResource // junto con la interfaz JpaRepository simplifica la creación de un servicio web RESTful,
public interface AccountRepository extends JpaRepository<Account, Long> { }