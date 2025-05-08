package com.sk.skala.stock.facade;

import com.sk.skala.stock.model.dto.response.StockResponse;
import java.util.List;

public interface StockFacade {
    /**
     * 모든 주식 정보 조회
     * @return 주식 정보 리스트
     */
    List<StockResponse> getAllStocks();

    /**
     * 주식 정보 조회
     * @param stockName 주식 이름
     * @return 주식 정보
     */
    StockResponse getStock(String stockName);

    /**
     * 시장 일일 시뮬레이션
     */
    void simulateMarketDay();
}
