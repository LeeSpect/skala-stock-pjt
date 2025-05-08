package com.sk.skala.transaction.controller;

import com.sk.skala.transaction.facade.TransactionFacade;
import com.sk.skala.transaction.model.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionFacade transactionFacade;

    @GetMapping("/player/{playerId}")
    public ResponseEntity<?> getTransactionsForPlayer(@PathVariable String playerId) {
        return ResponseEntity.ok(transactionFacade.getTransactionsForPlayer(playerId));
    }

    @GetMapping
    public ResponseEntity<?> getAllTransactions() {
        return ResponseEntity.ok(transactionFacade.getAllTransactions());
    }
}
