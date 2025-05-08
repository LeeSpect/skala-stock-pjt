package com.sk.skala.player.controller;

import com.sk.skala.player.model.dto.request.PlayerCreateRequest;
import com.sk.skala.player.model.dto.request.PlayerUpdateRequest;
import com.sk.skala.player.model.dto.response.PortfolioSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface PlayerControllerSpecification {

    @Tag(name = "Player", description = "Player 관련 API")
    @Operation(summary = "플레이어 생성", description = "플레이어를 생성합니다.")
    ResponseEntity<?> createPlayer(@Validated @RequestBody PlayerCreateRequest request);

    @Tag(name = "Player", description = "Player 관련 API")
    @Operation(summary = "플레이어 조회", description = "플레이어를 조회합니다.")
    ResponseEntity<?> getPlayer(@PathVariable String playerId);

    @Tag(name = "Player", description = "Player 관련 API")
    @Operation(summary = "플레이어 수정", description = "플레이어를 수정합니다.")
    ResponseEntity<?> updatePlayer(@PathVariable String playerId,
                                   @Validated @RequestBody PlayerUpdateRequest request);

    @Tag(name = "Player", description = "Player 관련 API")
    @Operation(summary = "주식 구매", description = "주식을 구매합니다.")
    ResponseEntity<?> buyStock(@PathVariable String playerId,
                               @RequestParam String stockName,
                               @RequestParam int quantity);


    @Tag(name = "Player", description = "Player 관련 API")
    @Operation(summary = "주식 판매", description = "주식을 판매합니다.")
    ResponseEntity<?> sellStock(@PathVariable String playerId,
                                @RequestParam String stockName,
                                @RequestParam int quantity);

    @Tag(name = "Player", description = "Player 관련 API")
    @Operation(summary = "포트폴리오 요약 조회", description = "포트폴리오 요약을 조회합니다.")
    ResponseEntity<PortfolioSummaryResponse> getPortfolioSummary(@PathVariable String playerId);
}
