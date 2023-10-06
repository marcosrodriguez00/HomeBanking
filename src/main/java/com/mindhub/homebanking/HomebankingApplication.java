package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
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

			Account cuenta3 = new Account("VIN003", 100000, today);
			Account cuenta4 = new Account("VIN004", 200000, tomorrow);
			marcos.addAccount(cuenta3);
			marcos.addAccount(cuenta4);
			accountRepository.save(cuenta3);
			accountRepository.save(cuenta4);

			LocalDateTime now = LocalDateTime.now();

			Transaction transaction1 = new Transaction(DEBIT, -2000, "Pago Matrícula", now);
			Transaction transaction2 = new Transaction(CREDIT, 5000, "Pago Colegio", now);
			Transaction transaction3 = new Transaction(DEBIT, -500, "Pago Mindhub", now);
			Transaction transaction4 = new Transaction(CREDIT, 4200, "Pago PASCAL", now);

			cuenta1.addTransaction(transaction1);
			cuenta1.addTransaction(transaction2);
			cuenta3.addTransaction(transaction3);
			cuenta4.addTransaction(transaction4);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
		};
	}
}
