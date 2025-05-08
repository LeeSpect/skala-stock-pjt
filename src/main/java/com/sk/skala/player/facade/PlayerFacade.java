package com.sk.skala.player.facade;

import com.sk.skala.player.model.dto.request.PlayerCreateRequest;
import com.sk.skala.player.model.dto.request.PlayerUpdateRequest;
import com.sk.skala.player.model.dto.response.PlayerResponse;
import com.sk.skala.player.model.dto.response.PortfolioSummaryResponse;

public interface PlayerFacade {
    /**
     * 새로운 플레이어를 생성합니다.
     * @param request 플레이어 생성 요청 DTO
     * @return 플레이어 응답 DTO
     */
    PlayerResponse createPlayer(PlayerCreateRequest request);

    /**
     * 플레이어를 조회합니다.
     * @param playerId 플레이어 ID
     * @return 플레이어 응답 DTO
     */
    PlayerResponse getPlayer(String playerId);

    /**
     * 플레이어 정보를 수정합니다.
     * @param playerId 플레이어 ID
     * @param request 플레이어 수정 요청 DTO
     * @return 플레이어 응답 DTO
     */
    PlayerResponse updatePlayer(String playerId, PlayerUpdateRequest request);

    /**
     * 주식을 구매합니다.
     * @param playerId 플레이어 ID
     * @param stockName 주식 이름
     * @param quantity 구매 수량
     * @return 구매 결과 메시지
     */
    String buyStock(String playerId, String stockName, int quantity);

    /**
     * 주식을 판매합니다.
     * @param playerId 플레이어 ID
     * @param stockName 주식 이름
     * @param quantity 판매 수량
     * @return 판매 결과 메시지
     */
    String sellStock(String playerId, String stockName, int quantity);

    /**
     * 포트폴리오 요약 정보를 조회합니다.
     * @param playerId 플레이어 ID
     * @return 포트폴리오 요약 응답 DTO
     */
    PortfolioSummaryResponse getPortfolioSummary(String playerId);
}
