package com.sk.skala.player.model.dto.response;

import com.sk.skala.player.model.entity.Player;
import com.sk.skala.player.model.entity.PlayerRank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PortfolioSummaryResponse {

    private final String playerId;
    private final String playerName;
    private final int playerMoney;
    private final long totalAsset;
    private final PlayerRank rank;
    private final LocalDateTime lastLoginTime;
    private final List<StockSummary> stocks;

    public static PortfolioSummaryResponse from(Player player) {
        return PortfolioSummaryResponse.builder()
                .playerId(player.getPlayerId())
                .playerName(player.getPlayerName())
                .playerMoney(player.getPlayerMoney())
                .totalAsset(player.getTotalAsset())
                .rank(player.getRank())
                .lastLoginTime(player.getLastLoginTime())
                .stocks(player.getPlayerStocks()
                        .stream()
                        .map(ps -> new StockSummary(
                                ps.getStock().getStockName(),
                                ps.getStock().getTickerSymbol(),
                                ps.getStock().getStockPrice(),
                                ps.getStockQuantity()))
                        .collect(Collectors.toList()))

                .build();
    }

    @Getter
    @Builder
    public static class StockSummary {
        private final String stockName;
        private final String tickerSymbol;
        private final int currentPrice;
        private final int quantity;

        public static StockSummary of(String stockName, String tickerSymbol, int currentPrice, int quantity) {
            return StockSummary.builder()
                    .stockName(stockName)
                    .tickerSymbol(tickerSymbol)
                    .currentPrice(currentPrice)
                    .quantity(quantity)
                    .build();
        }
    }
}
