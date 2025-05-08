package com.sk.skala.stock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface StockControllerSpecification {

    @Tag(name = "Stock Controller", description = "Stock 관련 API")
    @Operation(summary = "모든 주식 조회", description = "모든 주식을 조회합니다.")
    ResponseEntity<?> getAllStocks();

    @Tag(name = "Stock Controller", description = "Stock 관련 API")
    @Operation(summary = "특정 주식 조회", description = "특정 주식을 조회합니다.")
    ResponseEntity<?> getStock(@PathVariable String stockName);

    @Tag(name = "Stock Controller", description = "Stock 관련 API")
    @Operation(summary = "시장 일일 시뮬레이션", description = "시장을 하루 시뮬레이션합니다.")
    ResponseEntity<?> simulateMarketDay();

}
