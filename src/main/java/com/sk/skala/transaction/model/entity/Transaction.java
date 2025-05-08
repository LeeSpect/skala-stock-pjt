package com.sk.skala.transaction.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * 거래 내역 정보를 관리하는 엔티티 클래스
 * - 거래 ID, 플레이어 ID, 주식명, 거래 유형(BUY/SELL), 수량, 단위 가격, 거래 시각 등을 포함
 */
@Getter
@Setter
@Entity
@Builder
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @Column(name = "player_id", nullable = false)
    private String playerId;

    @Column(name = "stock_name", nullable = false)
    private String stockName;

    @Column(name = "type", nullable = false)
    private String type;           // "BUY" 또는 "SELL"

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "transaction_time", nullable = false)
    private LocalDateTime transactionTime;
}
