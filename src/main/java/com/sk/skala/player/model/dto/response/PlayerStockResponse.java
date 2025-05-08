package com.sk.skala.player.model.dto.response;

import com.sk.skala.player.model.entity.PlayerStock;
import lombok.Builder;
import lombok.Getter;

/**
 * 플레이어가 소유한 개별 주식 정보를 담는 응답 DTO
 */
@Getter
@Builder
public class PlayerStockResponse {

    private final String stockName;
    private final String tickerSymbol;
    private final int currentPrice;
    private final int quantity;

    public static PlayerStockResponse from(PlayerStock ps) {
        return PlayerStockResponse.builder()
                .stockName(ps.getStock().getStockName())
                .tickerSymbol(ps.getStock().getTickerSymbol())
                .currentPrice(ps.getStock().getStockPrice())
                .quantity(ps.getStockQuantity())
                .build();
    }
}
