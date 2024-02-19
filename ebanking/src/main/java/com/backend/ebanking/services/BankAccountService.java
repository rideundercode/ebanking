package com.backend.ebanking.services;

import com.backend.ebanking.dtos.*;
import com.backend.ebanking.entities.BankAccount;
import com.backend.ebanking.entities.CurrentAccount;
import com.backend.ebanking.entities.Customer;
import com.backend.ebanking.entities.SavingAccount;
import com.backend.ebanking.exceptions.BalanceNotSufficientException;
import com.backend.ebanking.exceptions.BankAccountNotFoundException;
import com.backend.ebanking.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    //Sauvegarder un Customer
    Customer saveCustomer(Customer customer);


    //Sauvegarder un compte
    CurrentAccount saveCurrentBankAccount(double initialBalance, double Overdraft, Long customerId) throws CustomerNotFoundException;

    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    // Quel est le solde initial, le type du compte, quel client


    //consulter une liste de client,
    List<CustomerDTO> listCustomers();


    //Pour consulter un compte j'aurais besoin de son id
    BankAccount GetBankAccount(String accountId) throws BankAccountNotFoundException;


    //operation de debit
    void debit(String accountId, double amount, String description) throws BalanceNotSufficientException, BankAccountNotFoundException;


    //operation de transfer d'argent
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;


    //operation de transfer d'argent
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccount> bankAccountList();


    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;


    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);
}










    /*
    //Sauvegarder un Customer
    SaveCustomer(Long id, String name, String email)

    //Sauvegarder un compte
    //consulter une liste de client,
    //Pour consulter un compte j'aurais besoin de son id
    //operation de debit
    //operation de transfer d'argent
    //operation de transfer d'argent
    */


}
