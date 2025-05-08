package com.sk.skala.transaction.facade;

import com.sk.skala.transaction.model.entity.Transaction;
import com.sk.skala.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionFacadeImpl implements TransactionFacade {

    private final TransactionService transactionService;

    @Override
    public List<Transaction> getTransactionsForPlayer(String playerId) {
        return transactionService.getTransactionsForPlayer(playerId);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }
}
