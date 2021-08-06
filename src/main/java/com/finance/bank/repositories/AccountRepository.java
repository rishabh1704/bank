package com.finance.bank.repositories;

import com.finance.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountById(Long id);
    List<Account> findAccountsByOwner_Id(Long customerId);
}
