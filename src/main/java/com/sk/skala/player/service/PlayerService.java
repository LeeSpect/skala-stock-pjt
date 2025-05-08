package com.sk.skala.player.service;

import com.sk.skala.player.model.dto.request.PlayerCreateRequest;
import com.sk.skala.player.model.dto.request.PlayerUpdateRequest;
import com.sk.skala.player.model.dto.response.PlayerResponse;
import com.sk.skala.player.model.dto.response.PortfolioSummaryResponse;

public interface PlayerService {
    /**
     * 플레이어 생성
     * @param request 플레이어 생성 요청 DTO
     * @return 플레이어 응답 DTO
     */
    PlayerResponse createPlayer(PlayerCreateRequest request);

    /**
     * 플레이어 조회
     * @param playerId 플레이어 ID(문자열)
     * @return 플레이어 응답 DTO
     */
    PlayerResponse getPlayer(String playerId);

    /**
     * 플레이어 수정
     * @param playerId 플레이어 ID(문자열)
     * @param request 플레이어 수정 요청 DTO
     * @return 플레이어 응답 DTO
     */
    PlayerResponse updatePlayer(String playerId, PlayerUpdateRequest request);

    /**
     * 주식 구매
     * @param playerId 플레이어 ID(문자열)
     * @param stockName 주식 이름(문자열)
     * @param quantity 주식 수량(정수)
     * @return 주식 구매 결과(문자열)
     */
    String buyStock(String playerId, String stockName, int quantity);

    /**
     * 주식 판매
     * @param playerId 플레이어 ID(문자열)
     * @param stockName 주식 이름(문자열)
     * @param quantity 주식 수량(정수)
     * @return 주식 판매 결과(문자열)
     */
    String sellStock(String playerId, String stockName, int quantity);

    /**
     * 포트폴리오 요약 조회
     * @param playerId 플레이어 ID(문자열)
     * @return 포트폴리오 요약 응답 DTO
     */
    PortfolioSummaryResponse getPortfolioSummary(String playerId);
}
