package com.sk.skala.stock.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.DiscriminatorType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.SINGLE_TABLE;

/**
 * 주식 정보 Entity
 * - 주가 이력은 별도 StockPriceHistory로 관리
 */
@Getter
@Setter
@Entity
@SuperBuilder
@Table(name = "stock")
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @Column(name = "stock_name", nullable = false)
    private String stockName;      // 주식 이름

    @Column(name = "ticker_symbol", nullable = false)
    private String tickerSymbol;   // 회사 코드

    @Column(name = "stock_price", nullable = false)
    private int stockPrice;        // 현재 주가

    @Column(name = "sector")
    private String sector;         // 업종/섹터 (예: "TECH", "ENERGY" 등)

    @OneToOne(cascade = ALL, orphanRemoval = true, fetch = LAZY)
    @JoinColumn(name = "stock_price_history_id")
    @Builder.Default
    private StockPriceHistory stockPriceHistory = StockPriceHistory.builder().build();

    public Stock(String stockName, String tickerSymbol, int stockPrice, String sector) {
        this.stockName = stockName;
        this.tickerSymbol = tickerSymbol;
        this.stockPrice = stockPrice;
        this.sector = sector;
        this.stockPriceHistory = new StockPriceHistory();
        this.stockPriceHistory.addPrice(stockPrice);
    }

    /**
     * 주가를 변경할 때 이력에 추가
     */
    public void updateStockPrice(int newPrice) {
        this.stockPrice = newPrice;
        this.stockPriceHistory.addPrice(newPrice);
    }

    /**
     * 가장 최근 가격 변동률(직전 가격 대비)
     */
    public double getLatestChangeRate() {
        return stockPriceHistory.getAveragePriceChangeRate(); // 예시
    }

    @Override
    public String toString() {
        return stockName +
                (tickerSymbol != null && !tickerSymbol.isEmpty() ? "(" + tickerSymbol + ")" : "") +
                ":" + stockPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Stock other)) return false;
        return Objects.equals(stockName, other.stockName)
                && Objects.equals(tickerSymbol, other.tickerSymbol)
                && stockPrice == other.stockPrice
                && Objects.equals(sector, other.sector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockName, tickerSymbol, stockPrice, sector);
    }
}
