package com.sk.skala.transaction.facade;

import com.sk.skala.transaction.model.entity.Transaction;
import java.util.List;

public interface TransactionFacade {
    /**
     * 플레이어의 거래 내역 조회
     * @param playerId 플레이어 ID(문자열)
     * @return 거래 내역 리스트
     */
    List<Transaction> getTransactionsForPlayer(String playerId);

    /**
     * 모든 거래 내역 조회
     * @return 거래 내역 리스트
     */
    List<Transaction> getAllTransactions();
}
