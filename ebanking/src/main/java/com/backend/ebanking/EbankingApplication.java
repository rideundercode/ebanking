package com.backend.ebanking;

import com.backend.ebanking.entities.AccountOperation;
import com.backend.ebanking.entities.CurrentAccount;
import com.backend.ebanking.entities.Customer;
import com.backend.ebanking.entities.SavingAccount;
import com.backend.ebanking.enums.AccountStatus;
import com.backend.ebanking.enums.OperationType;
import com.backend.ebanking.repositories.AccountOperationRepository;
import com.backend.ebanking.repositories.BankAccountRepository;
import com.backend.ebanking.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication //classe principale de l'application
public class EbankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingApplication.class, args);
	}

	
	//Le bean déclaré ici est de type CommandLineRunner,
	// ce qui signifie qu'il sera exécuté une fois que le contexte de l'application Spring est prêt.

	//dans les args, ce que je vais créer
	@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							AccountOperationRepository accountOperationRepository,
							BankAccountRepository bankAccountRepository){
		return args -> {
			//creation de trois clients
			Stream.of("Hassan", "Yassine", "Aicha").forEach(name ->{
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				customerRepository.save(customer);
			});

			//creation des comptes
			customerRepository.findAll().forEach(cust -> {
				CurrentAccount currentAccount= new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random()*9000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(cust);
				currentAccount.setOverDraft(9000);
				bankAccountRepository.save(currentAccount);


				SavingAccount savingAccount= new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random()*9000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(cust);
				savingAccount.setInterestRate(9000);
				bankAccountRepository.save(savingAccount);
			});

			//creation des operations
			bankAccountRepository.findAll().forEach(op -> {
				for(int i = 0; i <5 ; i++){
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random()*12000);
					accountOperation.setType((Math.random() > 0.5) ? OperationType.CREDIT : OperationType.DEBIT);
					accountOperation.setBankAccount(op);
					accountOperationRepository.save(accountOperation);
				}
			});
		};
	}
	
}
