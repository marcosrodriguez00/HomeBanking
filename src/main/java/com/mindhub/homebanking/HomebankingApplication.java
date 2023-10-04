package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository) {
		return (args) -> {
			Client marcos = new Client("Marcos", "Rodriguez", "marcosrodriguez3000@gmail.com");
			clientRepository.save(marcos);
			Client melba = new Client("Melba", "Morel", "melba@mindhub.com");
			clientRepository.save(melba);

			LocalDate today = LocalDate.now();
			LocalDate tomorrow = today.plusDays(1);
			Account cuenta1 = new Account("VIN001", 5000, today);
			Account cuenta2 = new Account("VIN002", 7500, tomorrow);
			melba.addAccount(cuenta1);
			melba.addAccount(cuenta2);
			accountRepository.save(cuenta1);
			accountRepository.save(cuenta2);
		};
	}
}
