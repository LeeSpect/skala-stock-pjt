package com.sk.skala.stock.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * 주가 이력 관리
 * - 주가 변동 시점마다 priceHistory에 기록
 */
@Getter
@Entity
@Setter
@Builder
@Table(name = "stock_price_history-84")
@NoArgsConstructor
@AllArgsConstructor
public class StockPriceHistory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "stock_price_history_id")
    private Long id;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "stock_price_history_value", joinColumns = @JoinColumn(name = "stock_price_history_id"))
    @Column(name = "price")
    private List<Integer> priceHistory = new ArrayList<>();

    public void addPrice(int price) {
        priceHistory.add(price);
    }

    public List<Integer> getHistory() {
        return Collections.unmodifiableList(priceHistory);
    }

    public double getAveragePrice() {
        if (priceHistory.isEmpty()) return 0;
        int sum = 0;
        for (int p : priceHistory)
            sum += p;
        return (double) sum / priceHistory.size();
    }

    public int getMaxPrice() {
        if (priceHistory.isEmpty()) return 0;
        return Collections.max(priceHistory);
    }

    public int getMinPrice() {
        if (priceHistory.isEmpty()) return 0;
        return Collections.min(priceHistory);
    }

    // 최근 변동률(= (현재가-직전가)/직전가*100)
    public double getAveragePriceChangeRate() {
        int size = priceHistory.size();
        if (size < 2) return 0.0;
        int currPrice = priceHistory.get(size - 1);
        int prevPrice = priceHistory.get(size - 2);
        if (prevPrice == 0) return 0.0;
        return ((double) (currPrice - prevPrice) / prevPrice) * 100.0;
    }
}
