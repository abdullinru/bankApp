package ru.github.abdullinru.bankapp.bankApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.github.abdullinru.bankapp.bankApp.model.Account;
import ru.github.abdullinru.bankapp.bankApp.model.History;
import ru.github.abdullinru.bankapp.bankApp.model.OperationType;
import ru.github.abdullinru.bankapp.bankApp.model.Owner;
import ru.github.abdullinru.bankapp.bankApp.repository.AccountRepository;
import ru.github.abdullinru.bankapp.bankApp.repository.HistoryRepository;
import ru.github.abdullinru.bankapp.bankApp.repository.OwnerRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
public class BankAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAppApplication.class, args);
	}
	@Bean
	public CommandLineRunner CommandLineRunnerBean(
			OwnerRepository ownerRepository,
			AccountRepository accountRepository,
			HistoryRepository historyRepository) {
		return (args) -> {
			Owner ruslan = new Owner(1L, "ruslan", "1111");
			Owner sergey = new Owner(2L, "Sergey", "2222");
			Owner olia = new Owner(3L, "Olia", "3333");
			Account ruslanAccount = new Account(1L, "1111 2222 3333 4444", BigDecimal.valueOf(1000), ruslan);
			Account sergeyAccount = new Account(2L, "2222 3333 4444 5555", BigDecimal.valueOf(2000), sergey);
			Account oliaAccount = new Account(3L, "3333 4444 5555 6666", BigDecimal.valueOf(3000), olia);
			History log1 = new History(1L, LocalDateTime.now(), OperationType.deposit, 0L, 2L, BigDecimal.valueOf(100));
			History log2 = new History(2L, LocalDateTime.now(), OperationType.withdraw, 1L, 0L, BigDecimal.valueOf(100));
			History log3 = new History(3L, LocalDateTime.now(), OperationType.transfer, 1L, 2L, BigDecimal.valueOf(100));
			ownerRepository.save(ruslan);
			ownerRepository.save(sergey);
			ownerRepository.save(olia);
			accountRepository.save(ruslanAccount);
			accountRepository.save(sergeyAccount);
			accountRepository.save(oliaAccount);
			historyRepository.save(log1);
			historyRepository.save(log2);
			historyRepository.save(log3);
		};
	}

}
