package com.example.salessystem.repository;

import com.example.salessystem.model.SaleTransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleTransactionLogRepository extends JpaRepository<SaleTransactionLog, Long> {
}
