package com.backend.ebanking.services;

import com.backend.ebanking.entities.*;
import com.backend.ebanking.enums.OperationType;
import com.backend.ebanking.exceptions.BalanceNotSufficientException;
import com.backend.ebanking.exceptions.CustomerNotFoundException;
import com.backend.ebanking.exceptions.BankAccountNotFoundException;
import com.backend.ebanking.repositories.BankAccountRepository;
import com.backend.ebanking.repositories.CustomerRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl extends BankService implements BankAccountService{

    //@Autowired : injection des dependances
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperation accountOperation;


    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("Saving new customer");
        return customerRepository.save(customer);
    }

    ;


    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new CustomerNotFoundException("Customer not found");

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        return bankAccountRepository.save(currentAccount);
    }

    ;


    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        return bankAccountRepository.save(savingAccount);
    }


    // Quel est le solde initial, le type du compte, quel client
    public List<Customer> listCustomers(){
        return customerRepository.findAll();            
    };

    //Pour consulter un compte j'aurais besoin de son id
    public BankAccount GetBankAccount(String accountId) throws BankAccountNotFoundException{
        return bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));
    };

    public void debit(String accountId, double amount, String description) throws BalanceNotSufficientException, BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));
        if(bankAccount.getBalance() < amount)
            throw new BalanceNotSufficientException("solde is enough");

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationDate(new Date());
        accountOperation.setAmount(amount);
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setDescription(description);
        bankAccount.setBalance(bankAccount.getBalance() -amount);
        bankAccountRepository.save(bankAccount);   
    };

    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationDate(new Date());
        accountOperation.setAmount(amount);
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setDescription(description);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    };



    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        credit(accountIdSource, amount, "transfer to " + accountIdDestination);
        debit(accountIdSource, amount, "transfer from" + accountIdSource);
    };

    @Override
    public List<BankAccount> bankAccountList(){
        return bankAccountRepository.findAll();
    }

}
