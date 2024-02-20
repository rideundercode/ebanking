package com.backend.ebanking.web;

import com.backend.ebanking.exceptions.CustomerNotFoundException;
import com.backend.ebanking.dtos.CustomerDTO;
import com.backend.ebanking.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {

    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name ="id") Long customerId) throws CustomerNotFoundException {
      return bankAccountService.getCustomer(customerId);
    }

}
