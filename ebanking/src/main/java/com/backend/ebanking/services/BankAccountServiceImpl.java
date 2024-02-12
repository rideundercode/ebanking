package com.backend.ebanking.services;

import com.backend.ebanking.entities.*;
import com.backend.ebanking.repositories.BankAccountRepository;
import com.backend.ebanking.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl extends BankService{

    //@Autowired: injection des dependances

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperation accountOperation;

    /*public BankAccountServiceImpl(CustomerRepository customerRepository,
                                  BankAccountRepository bankAccountRepository,
                                  AccountOperation accountOperation){
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.accountOperation = accountOperation;
    }*/

    //Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    Customer SaveCustomer(Customer customer){
        log.info("Saving new customer");
        Customer customerSaved = customerRepository.save(customer);
        return customerSaved;
    };
    BankAccount SaveBankAccount(double initialBalance, String type, Long customerId){
        log.info("saving bankaccount");

        BankAccount bankAccount;
        if(type.equals("current")){
            bankAccount = new CurrentAccount();
        }else{
            bankAccount =new SavingAccount();
        }

        
    };
    // Quel est le solde initial, le type du compte, quel client

    List<Customer> ListCustomers(){ return null; };

    //Pour consulter un compte j'aurais besoin de son id
    BankAccount GetBankAccount(String accountId){ return null; };

    void debit(String accountId, double amount, String description){ };

    void credit(String accountId, double amount, String description){ };

    void transfer(String accountIdSource, String accountIdDestination, double amount){ };

}
