package com.sk.skala.player.service;

import com.sk.skala.player.model.dto.request.PlayerCreateRequest;
import com.sk.skala.player.model.dto.request.PlayerUpdateRequest;
import com.sk.skala.player.model.dto.response.PlayerResponse;
import com.sk.skala.player.model.dto.response.PortfolioSummaryResponse;
import com.sk.skala.player.model.entity.Player;
import com.sk.skala.player.model.entity.PlayerStock;
import com.sk.skala.player.repository.PlayerRepository;
import com.sk.skala.stock.model.entity.Stock;
import com.sk.skala.stock.repository.StockRepository;
import com.sk.skala.transaction.model.entity.Transaction;
import com.sk.skala.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final StockRepository stockRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public PlayerResponse createPlayer(PlayerCreateRequest request) {
        if (playerRepository.existsByPlayerId(request.getPlayerId()))
            throw new RuntimeException("플레이어가 이미 존재합니다.");
        Player player = Player.builder()
                .playerId(request.getPlayerId())
                .playerName(request.getPlayerName())
                .playerMoney(request.getInitialMoney())
                .build();
        return PlayerResponse.from(playerRepository.save(player));
    }

    @Override
    public PlayerResponse getPlayer(String playerId) {
        Player player = playerRepository.findByPlayerId(playerId);
        if (player == null) throw new RuntimeException("플레이어가 없습니다.");
        return PlayerResponse.from(player);
    }

    @Override
    @Transactional
    public PlayerResponse updatePlayer(String playerId, PlayerUpdateRequest request) {
        Player player = playerRepository.findByPlayerId(playerId);
        if (player == null) throw new RuntimeException("플레이어가 없습니다.");
        if (request.getPlayerName() != null)
            player.setPlayerName(request.getPlayerName());
        if (request.getPlayerMoney() >= 0)
            player.setPlayerMoney(request.getPlayerMoney());
        player.setLastLoginTime(LocalDateTime.now());
        return PlayerResponse.from(playerRepository.save(player));
    }

    @Override
    @Transactional
    public String buyStock(String playerId, String stockName, int quantity) {
        Player player = playerRepository.findByPlayerId(playerId);
        if (player == null) throw new RuntimeException("플레이어가 없습니다.");

        Stock stock = stockRepository.findByStockNameIgnoreCase(stockName);
        if (stock == null) throw new RuntimeException("플레이어가 없습니다.");

        int totalCost = stock.getStockPrice() * quantity;
        if (totalCost > player.getPlayerMoney()) throw new RuntimeException("보유 현금이 부족합니다.");

        player.setPlayerMoney(player.getPlayerMoney() - totalCost);

        // 플레이어가 이미 해당 주식을 가지고 있는지 확인
        boolean found = false;
        for (PlayerStock ps : player.getPlayerStocks()) {
            if (ps.getStock(). getStockName().equalsIgnoreCase(stockName)) {
                ps.setStockQuantity(ps.getStockQuantity() + quantity);
                found = true;
                break;
            }
        }
        if (!found) {
            // 새 PlayerStock 엔티티 생성 후, player와 연관관계 생성
            player.getPlayerStocks().add(PlayerStock.builder()
                    .player(player)
                    .stock(stock)
                    .stockQuantity(quantity)
                    .build());
        }
        player.updateRank();
        playerRepository.save(player);

        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .playerId(playerId)
                .stockName(stockName)
                .type("BUY")
                .quantity(quantity)
                .price(stock.getStockPrice())
                .transactionTime(LocalDateTime.now())
                .build();
        transactionRepository.save(transaction);
        return "주식 매수에 성공했습니다.";
    }

    @Override
    @Transactional
    public String sellStock(String playerId, String stockName, int quantity) {
        Player player = playerRepository.findByPlayerId(playerId);
        if (player == null) throw new RuntimeException("플레이어가 없습니다.");

        PlayerStock targetPs = player.getPlayerStocks().stream()
                .filter(ps -> ps.getStock().getStockName().equalsIgnoreCase(stockName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("플레이어가 해당 주식을 가지고 있지 않습니다."));
        if (quantity > targetPs.getStockQuantity()) throw new RuntimeException("가지고 있는 주식이 부족합니다.");

        Stock stock = stockRepository.findByStockNameIgnoreCase(stockName);
        if (stock == null) throw new RuntimeException("주식이 없습니다.");

        int saleAmount = stock.getStockPrice() * quantity;
        player.setPlayerMoney(player.getPlayerMoney() + saleAmount);
        targetPs.setStockQuantity(targetPs.getStockQuantity() - quantity);
        // 차감 후, 수량이 0이면 리스트에서 제거
        if (targetPs.getStockQuantity() == 0)
            player.getPlayerStocks().remove(targetPs);
        player.updateRank();
        playerRepository.save(player);

        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .playerId(playerId)
                .stockName(stockName)
                .type("SELL")
                .quantity(quantity)
                .price(stock.getStockPrice())
                .transactionTime(LocalDateTime.now())
                .build();
        transactionRepository.save(transaction);
        return "주식 매도에 성공했습니다.";
    }

    @Override
    public PortfolioSummaryResponse getPortfolioSummary(String playerId) {
        Player player = playerRepository.findByPlayerId(playerId);
        if (player == null) throw new RuntimeException("플레이어가 없습니다.");
        return PortfolioSummaryResponse.from(player);
    }

}
