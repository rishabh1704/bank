package com.finance.bank.repositories;

import com.finance.bank.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
    public TestEntity findTestEntityById(Long id);
}
