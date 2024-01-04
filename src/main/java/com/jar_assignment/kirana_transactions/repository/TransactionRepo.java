package com.jar_assignment.kirana_transactions.repository;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.jar_assignment.kirana_transactions.model.Transactions;
import com.jar_assignment.kirana_transactions.model.User;

public interface TransactionRepo  extends JpaRepository<Transactions, Integer>{
    List<Transactions> findAllByUserOrderByTransactionTimeDesc(User user);
    List<Transactions> findAllByUserAndTransactionTimeGreaterThanEqualOrderByTransactionTimeDesc(User user, LocalDateTime date);
    // Page<Transactions> findAllByUser(User user, Pageable pageable);
    // Page<Transactions> findAllByUserAndTransactionTimeGreaterThanEqual(User user, LocalDateTime date, Pageable pageable);

}
