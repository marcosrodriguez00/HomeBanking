package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.mindhub.homebanking.models.CardColor.*;
import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication	// Dice que la aplicación de abajo es una app de springboot, crea el contexto de springboot
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
//			Client marcos = new Client("Marcos", "Rodriguez", "marcosrodriguez3000@gmail.com", passwordEncoder.encode("marcos123"), true);
//			clientRepository.save(marcos);
//			Client melba = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba123"), false);
//			clientRepository.save(melba);
//
//			LocalDate today = LocalDate.now();
//			LocalDate tomorrow = today.plusDays(1);
//			Account cuenta1 = new Account("VIN001", 5000, today);
//			Account cuenta2 = new Account("VIN002", 7500, tomorrow);
//			melba.addAccount(cuenta1);
//			melba.addAccount(cuenta2);
//			accountRepository.save(cuenta1);
//			accountRepository.save(cuenta2);
//
//			Account cuenta3 = new Account("VIN003", 100000, today);
//			Account cuenta4 = new Account("VIN004", 200000, tomorrow);
//			marcos.addAccount(cuenta3);
//			marcos.addAccount(cuenta4);
//			accountRepository.save(cuenta3);
//			accountRepository.save(cuenta4);
//
//			LocalDateTime dateTime = LocalDateTime.now();
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//			String formattedDateTime = dateTime.format(formatter);
//			LocalDateTime formattedLocalDateTime = LocalDateTime.parse(formattedDateTime, formatter);
//
//			Transaction transaction1 = new Transaction(DEBIT, -2000, "Pago Matrícula", formattedLocalDateTime);
//			Transaction transaction2 = new Transaction(CREDIT, 5000, "Pago Colegio", formattedLocalDateTime);
//			Transaction transaction3 = new Transaction(DEBIT, -500, "Pago Mindhub", formattedLocalDateTime);
//			Transaction transaction4 = new Transaction(CREDIT, 4200, "Pago PASCAL", formattedLocalDateTime);
//
//			cuenta1.addTransaction(transaction1);
//			cuenta1.addTransaction(transaction2);
//			cuenta3.addTransaction(transaction3);
//			cuenta4.addTransaction(transaction4);
//
//			transactionRepository.save(transaction1);
//			transactionRepository.save(transaction2);
//			transactionRepository.save(transaction3);
//			transactionRepository.save(transaction4);
//
//			Loan prestamo1 = new Loan("Mortgage", 500000, List.of(12, 24, 36, 48, 60));
//			Loan prestamo2 = new Loan("Personal", 100000, List.of(6, 12, 24));
//			Loan prestamo3 = new Loan("Car", 300000, List.of(6, 12, 24, 36));
//
//			loanRepository.save(prestamo1);
//			loanRepository.save(prestamo2);
//			loanRepository.save(prestamo3);
//
//			// Estos constructores funcionan pero no cumplen con el principio de responsabilidad única
//			ClientLoan clientLoan1 = new ClientLoan(400000, melba, prestamo1, 60);
//			ClientLoan clientLoan2 = new ClientLoan(50000, melba, prestamo2, 12);
//			ClientLoan clientLoan3 = new ClientLoan(100000, marcos, prestamo2, 24);
//			ClientLoan clientLoan4 = new ClientLoan(200000, marcos, prestamo3, 36);
//
//			ClientLoan clientLoan5 = new ClientLoan(10000, 12);
//			marcos.addClientLoan(clientLoan5);
//			prestamo2.addClientLoan(clientLoan5);
//			ClientLoan clientLoan6 = new ClientLoan(200000, 36);
//			melba.addClientLoan(clientLoan6);
//			prestamo1.addClientLoan(clientLoan6);
//
//			clientLoanRepository.save(clientLoan1);
//			clientLoanRepository.save(clientLoan2);
//			clientLoanRepository.save(clientLoan3);
//			clientLoanRepository.save(clientLoan4);
//			clientLoanRepository.save(clientLoan5);
//			clientLoanRepository.save(clientLoan6);
//
//			LocalDate fiveYears = today.plusYears(5);
//
//			Card card1 = new Card(melba.fullName(), "1111-1111-1111-1111", "123", CardType.DEBIT, GOLD, fiveYears, today);
//			melba.addCard(card1);
//
//			Card card2 = new Card(melba.fullName(), "2222-2222-2222-2222", "321", CardType.CREDIT, TITANIUM, fiveYears, today);
//			melba.addCard(card2);
//
//			Card card3 = new Card(marcos.fullName(), "3333-3333-3333-3333", "333", CardType.CREDIT, SILVER, fiveYears, today);
//			marcos.addCard(card3);
//
//			cardRepository.save(card1);
//			cardRepository.save(card2);
//			cardRepository.save(card3);
		};
	}

	// Password Encoder

	@Autowired
	private PasswordEncoder passwordEncoder;
}
