package com.solomon.nkwor.solomon.bank.Repository;

import com.solomon.nkwor.solomon.bank.Model.Transactions;
import jakarta.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transactions, String> {
}
