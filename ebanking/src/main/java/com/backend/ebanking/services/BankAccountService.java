package com.backend.ebanking.services;

import com.backend.ebanking.entities.BankAccount;
import com.backend.ebanking.entities.Customer;

import java.util.List;

public interface BankAccountService {

    //Sauvegarder un Customer
    Customer SaveCustomer(Customer customer);


    //Sauvegarder un compte
    BankAccount SaveBankAccount(double initialBalance, String type, Long customerId);
    // Quel est le solde initial, le type du compte, quel client


    List<Customer> ListCustomers();


    //Pour consulter un compte j'aurais besoin de son id
    BankAccount GetBankAccount(String accountId);


    void debit(String accountId, double amount, String description);


    void credit(String accountId, double amount, String description);


    void transfer(String accountIdSource, String accountIdDestination, double amount);
    
}
