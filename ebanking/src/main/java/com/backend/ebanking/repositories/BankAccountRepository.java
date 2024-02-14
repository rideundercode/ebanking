package com.backend.ebanking.repositories;

import com.backend.ebanking.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
