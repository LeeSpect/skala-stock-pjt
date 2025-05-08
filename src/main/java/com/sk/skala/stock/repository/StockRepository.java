package com.sk.skala.stock.repository;

import com.sk.skala.stock.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
    /**
     * 주식 이름으로 주식 조회
     * @param stockName 주식 이름
     * @return 주식
     */
    Stock findByStockNameIgnoreCase(String stockName);
}
