package com.jar_assignment.kirana_transactions.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.jar_assignment.kirana_transactions.model.Transactions;
import com.jar_assignment.kirana_transactions.model.User;
import org.springframework.stereotype.Service;

import com.jar_assignment.kirana_transactions.repository.TransactionRepo;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepo repository;
    public String addTransaction(Transactions transactions){
        repository.save(transactions);
        return "Record created !!!";
    }
    public List<Transactions> getTransactionsByUserAndDate(User user, Optional<LocalDateTime> date) {
        if (date.isPresent()){
            return repository.findAllByUserAndTransactionTimeGreaterThanEqualOrderByTransactionTimeDesc(user, date.get());
        }
        return repository.findAllByUserOrderByTransactionTimeDesc(user);
    }

}
