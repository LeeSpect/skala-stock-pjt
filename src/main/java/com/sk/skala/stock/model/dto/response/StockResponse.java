package com.sk.skala.stock.model.dto.response;

import com.sk.skala.stock.model.entity.Stock;
import lombok.Builder;
import lombok.Getter;

/**
 * 주식 정보를 응답 DTO
 */
@Getter
@Builder
public class StockResponse {

    private final String stockName;
    private final String tickerSymbol;
    private final int currentPrice;
    private final double latestChangeRate; // 최근 변동률
    private final double averagePrice;
    private final int maxPrice;
    private final int minPrice;
    private final String sector;

    public static StockResponse from(Stock stock) {
        return StockResponse.builder()
                .stockName(stock.getStockName())
                .tickerSymbol(stock.getTickerSymbol())
                .currentPrice(stock.getStockPrice())
                .latestChangeRate(stock.getLatestChangeRate())
                .averagePrice(stock.getStockPriceHistory().getAveragePrice())
                .maxPrice(stock.getStockPriceHistory().getMaxPrice())
                .minPrice(stock.getStockPriceHistory().getMinPrice())
                .sector(stock.getSector())
                .build();
    }
}
