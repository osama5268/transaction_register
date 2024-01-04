package com.jar_assignment.kirana_transactions.controller;

import com.jar_assignment.kirana_transactions.model.User;
import com.jar_assignment.kirana_transactions.model.Transactions;
import com.jar_assignment.kirana_transactions.model.Transactions.Currency;
import com.jar_assignment.kirana_transactions.model.Transactions.TransactionType;
import com.jar_assignment.kirana_transactions.service.CurrencyConversionService;
import com.jar_assignment.kirana_transactions.service.TransactionService;
import com.jar_assignment.kirana_transactions.service.UserInfoService;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*; 

@RestController
@RequestMapping("/transaction") 
public class TransactionController { 

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserInfoService userService;

    @Autowired
    private CurrencyConversionService currencyConversionService;


	@PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> addNewTransaction(@RequestBody Transactions transaction) { 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            User user = userService.findbyUsername(username);
            transaction.setuser(user);
            Double amount = transaction.getTransactionAmount();
            if (transaction.gettransactionCurrency() == Currency.USD){
                Double exchange_rate = currencyConversionService.getCurrentConversionRate();
                System.out.println(exchange_rate);
                Double amount_inr = amount * exchange_rate;
                transaction.setTransactionAmountINR(amount_inr);
                transaction.setTransactionAmountUSD(amount);
            }
            else if (transaction.gettransactionCurrency() == Currency.INR){
                Double amount_usd = amount / (Double)currencyConversionService.getCurrentConversionRate();
                transaction.setTransactionAmountINR(amount);
                transaction.setTransactionAmountUSD(amount_usd);
            }
            transactionService.addTransaction(transaction);
            return ResponseEntity.ok().body(Map.of("data", "success"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("data", "An error occured while adding transaction"));
        }

	}

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getTransactions(@RequestParam Optional<LocalDateTime> lastTransactionTime) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            User user = userService.findbyUsername(username);
            List<Transactions> transactions = transactionService.getTransactionsByUserAndDate(user, lastTransactionTime);

            Map<String, Map<String, Object>> transactionsByDate = new HashMap<>();

            for (Transactions transaction : transactions) {
                String date = transaction.gettransactionTime().toLocalDate().toString();
                Map<String, Object> dateMap = transactionsByDate.computeIfAbsent(date, k -> new HashMap<>(Map.of(
                    "transactions", new ArrayList<>(),
                    "total_credit_amount_inr", 0.0,
                    "total_credit_amount_usd", 0.0,
                    "total_debit_amount_inr", 0.0,
                    "total_debit_amount_usd", 0.0
                )));

                List<Map<String, Object>> transactionList = (List<Map<String, Object>>) dateMap.get("transactions");

                Map<String, Object> content = new HashMap<>();
                Double amount_inr = new BigDecimal(transaction.getTransactionAmountINR()).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                Double amount_usd = new BigDecimal(transaction.getTransactionAmountUSD()).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                content.put("transaction_id", transaction.getid());
                content.put("amount_inr", amount_inr);
                content.put("amount_usd", amount_usd);
                content.put("transaction_time", transaction.gettransactionTime());
                content.put("transaction_type", transaction.gettransactionType());
                transactionList.add(content);

                if (transaction.gettransactionType() == TransactionType.CREDIT) {
                    dateMap.put("total_credit_amount_inr", (Double) dateMap.get("total_credit_amount_inr") + amount_inr);
                    dateMap.put("total_credit_amount_usd", (Double) dateMap.get("total_credit_amount_usd") + amount_usd);
                } else {
                    dateMap.put("total_debit_amount_inr", (Double) dateMap.get("total_debit_amount_inr") + amount_inr);
                    dateMap.put("total_debit_amount_usd", (Double) dateMap.get("total_debit_amount_usd") + amount_usd);
                }
            }

            return ResponseEntity.ok(Map.of("data", transactionsByDate));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", "An error occurred while fetching transactions."));
        }
    }
} 
