package com.backend.ebanking;

import com.backend.ebanking.entities.*;
import com.backend.ebanking.enums.AccountStatus;
import com.backend.ebanking.enums.OperationType;
import com.backend.ebanking.exceptions.BalanceNotSufficientException;
import com.backend.ebanking.exceptions.BankAccountNotFoundException;
import com.backend.ebanking.exceptions.CustomerNotFoundException;
import com.backend.ebanking.repositories.AccountOperationRepository;
import com.backend.ebanking.repositories.BankAccountRepository;
import com.backend.ebanking.repositories.CustomerRepository;
import com.backend.ebanking.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication //classe principale de l'application
@ComponentScan(basePackages = "com.backend.ebanking")
public class EmbankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmbankingApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner (BankAccountService bankAccountService) {
		return args -> {
			Stream.of("Hassan", "Imane", "Patick").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				bankAccountService.saveCustomer(customer);
			});
			bankAccountService.listCustomers().forEach(customer -> {
				try {
					bankAccountService.saveCurrentBankAccount(Math.random() * 9000, 9000, customer.getId());
					bankAccountService.saveCurrentBankAccount(Math.random() * 12000, 5.5, customer.getId());

					//consulter tout les comptes
					List<BankAccount> bankAccounts =bankAccountService.bankAccountList();
					for(BankAccount bankAccount : bankAccounts){
						for (int i = 0; i < 10 ; i++){
							bankAccountService.credit(bankAccount.getId(), 1000 + Math.random() * 100000, "Credit");
							bankAccountService.debit(bankAccount.getId(), 1000 + Math.random() * 20, "Debit");
						}
					}
					/*
					bankAccountService.bankAccountList().forEach(account -> {
						for (int i = 0; i < 10 ; i++)
							bankAccountService.credit(account.getId(), 1000 + Math.random() * 100000, "Credit");
							bankAccountService.debit(account.getId(), 1000 + Math.random() * 100000, "Debit");
					});
					*/
				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				} catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
					throw new RuntimeException(e);
				}
			});
		};
	} 

					

	
	//Le bean déclaré ici est de type CommandLineRunner,
	// ce qui signifie qu'il sera exécuté une fois que le contexte de l'application Spring est prêt.

	//dans les args, ce que je vais créer
	//@Bean
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
