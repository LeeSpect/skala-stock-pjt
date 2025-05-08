package com.sk.skala.player.model.dto.response;

import com.sk.skala.player.model.entity.Player;
import com.sk.skala.player.model.entity.PlayerRank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 플레이어 정보를 응답할 때 사용하는 DTO
 * - Entity(Player) -> Response 변환
 */
@Getter
@Builder
public class PlayerResponse {

    private final String playerId;
    private final String playerName;
    private final int playerMoney;
    private final long totalAsset;
    private final PlayerRank rank;
    private final LocalDateTime creationTime;
    private final LocalDateTime lastLoginTime;
    private final List<PlayerStockResponse> playerStocks;

    public static PlayerResponse from(Player player) {
        return PlayerResponse.builder()
                .playerId(player.getPlayerId())
                .playerName(player.getPlayerName())
                .playerMoney(player.getPlayerMoney())
                .totalAsset(player.getTotalAsset())
                .rank(player.getRank())
                .creationTime(player.getCreationTime())
                .lastLoginTime(player.getLastLoginTime())
                .playerStocks(player.getPlayerStocks()
                        .stream()
                        .map(PlayerStockResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
