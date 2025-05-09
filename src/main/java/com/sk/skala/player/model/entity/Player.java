package com.sk.skala.player.model.entity;

import com.sk.skala.stock.model.entity.Stock;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

/**
 * 플레이어 정보를 관리하는 Entity 클래스
 * - DB 연동이 아닌 파일/메모리에 저장
 * - 추가 아이디어:
 * 1) creationTime, lastLoginTime 필드
 * 2) rank(등급) 필드: 보유 자산에 따라 동적으로 계산 가능
 * 3) getTotalAsset() 메서드: 현재 보유 자산 + 주식 평가액
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "player-84")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Player {

    @Id
    @Column(name = "player_id", nullable = false, unique = true)
    private String playerId;

    @Column(name = "player_name", nullable = false)
    private String playerName;

    @Builder.Default
    @Column(name = "player_money", nullable = false)
    private int playerMoney = 0; // 보유 현금

    // 플레이어 생성 시각
    @Builder.Default
    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime = LocalDateTime.now();

    // 마지막 로그인(또는 마지막 활동) 시각
    @Builder.Default
    @Column(name = "last_login_time", nullable = false)
    private LocalDateTime lastLoginTime = LocalDateTime.now();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "player_rank", nullable = false)
    private PlayerRank rank = PlayerRank.BRONZE; // 등급

    // 플레이어가 소유한 주식 목록
    @Builder.Default
    @OneToMany(mappedBy = "player", cascade = ALL, fetch = LAZY, orphanRemoval = true)
    private List<PlayerStock> playerStocks = new ArrayList<>();

    /**
     * 플레이어가 소유한 주식의 현재 평가액 + playerMoney 합산
     */
    public long getTotalAsset() {
        long totalStockValue = 0;
        if (!CollectionUtils.isEmpty(playerStocks)) {
            for (PlayerStock ps : playerStocks)
                totalStockValue += (long) ps.getStock().getStockPrice() * ps.getStockQuantity();
        }
        return totalStockValue + playerMoney;
    }

    /**
     * 주식 매입/매수 등으로 자산이 변동될 때, 등급(rank) 업데이트
     * (예: 5만 미만 -> BRONZE, 5만 이상 -> SILVER, 10만 이상 -> GOLD, etc.)
     */
    public void updateRank() {
        long totalAsset = getTotalAsset();
        if (totalAsset >= 100_000)
            rank = PlayerRank.GOLD;
        else if (totalAsset >= 50_000)
            rank = PlayerRank.SILVER;
        else
            rank = PlayerRank.BRONZE;
    }

    /**
     * 주식 구매/추가 로직 (파일/DB 저장은 Repository에서 처리)
     */
    public void addStock(Stock stock, int quantity) {
        // 이미 보유 중인 주식이면 ~
        for (PlayerStock existing : playerStocks) {
            if (existing.getStock().getStockName().equals(stock.getStockName())) {
                existing.setStockQuantity(existing.getStockQuantity() + quantity);
                // 현재 주가 갱신
                existing.getStock().updateStockPrice(stock.getStockPrice());
                return;
            }
        }
        // 없으면 새로 추가
        PlayerStock newStock = new PlayerStock(stock, quantity);
        playerStocks.add(newStock);
    }

    /**
     * 주식 판매 로직 (수량 감소)
     */
    public void removeStock(String stockName, int quantity) {
        for (PlayerStock existing : playerStocks) {
            if (existing.getStock().getStockName().equals(stockName)) {
                int remain = existing.getStockQuantity() - quantity;
                // 수량이 0 이하가 되면 보유 목록에서 제거
                if (remain <= 0)
                    playerStocks.remove(existing);
                else existing.setStockQuantity(remain);
                return;
            }
        }
    }
}
