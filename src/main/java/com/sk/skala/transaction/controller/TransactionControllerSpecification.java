package com.sk.skala.transaction.controller;

import com.sk.skala.transaction.model.entity.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TransactionControllerSpecification {

    @Tag(name = "Transaction", description = "Transaction 관련 API")
    @Operation(summary = "플레이어의 거래 내역 조회", description = "플레이어의 거래 내역을 조회합니다.")
    ResponseEntity<?> getTransactionsForPlayer(@PathVariable String playerId);

    @Tag(name = "Transaction", description = "Transaction 관련 API")
    @Operation(summary = "모든 거래 내역 조회", description = "모든 거래 내역을 조회합니다.")
    ResponseEntity<?> getAllTransactions();

}
