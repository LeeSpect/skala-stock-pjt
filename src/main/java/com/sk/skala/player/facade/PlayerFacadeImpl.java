package com.sk.skala.player.facade;

import com.sk.skala.player.model.dto.request.PlayerCreateRequest;
import com.sk.skala.player.model.dto.request.PlayerUpdateRequest;
import com.sk.skala.player.model.dto.response.PlayerResponse;
import com.sk.skala.player.model.dto.response.PortfolioSummaryResponse;
import com.sk.skala.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerFacadeImpl implements PlayerFacade {

    private final PlayerService playerService;

    @Override
    @Transactional
    public PlayerResponse createPlayer(PlayerCreateRequest request) {
        return playerService.createPlayer(request);
    }

    @Override
    public PlayerResponse getPlayer(String playerId) {
        return playerService.getPlayer(playerId);
    }

    @Override
    @Transactional
    public PlayerResponse updatePlayer(String playerId, PlayerUpdateRequest request) {
        return playerService.updatePlayer(playerId, request);
    }

    @Override
    @Transactional
    public String buyStock(String playerId, String stockName, int quantity) {
        return playerService.buyStock(playerId, stockName, quantity);
    }

    @Override
    @Transactional
    public String sellStock(String playerId, String stockName, int quantity) {
        return playerService.sellStock(playerId, stockName, quantity);
    }

    @Override
    public PortfolioSummaryResponse getPortfolioSummary(String playerId) {
        return playerService.getPortfolioSummary(playerId);
    }
}
