package com.sk.skala.transaction.repository;

import com.sk.skala.transaction.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    /**
     * 플레이어 ID로 거래 내역 조회
     * @param playerId 플레이어 ID(문자열)
     * @return 거래 내역 리스트
     */
    List<Transaction> findAllByPlayerId(String playerId);
}