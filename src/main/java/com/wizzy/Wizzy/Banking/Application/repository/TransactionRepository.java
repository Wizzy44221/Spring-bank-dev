package com.wizzy.Wizzy.Banking.Application.repository;

import com.wizzy.Wizzy.Banking.Application.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
