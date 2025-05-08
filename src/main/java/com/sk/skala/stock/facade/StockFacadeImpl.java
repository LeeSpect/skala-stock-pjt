package com.sk.skala.stock.facade;

import com.sk.skala.stock.model.dto.response.StockResponse;
import com.sk.skala.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockFacadeImpl implements StockFacade {

    private final StockService stockService;

    @Override
    public List<StockResponse> getAllStocks() {
        return stockService.getAllStocks();
    }

    @Override
    public StockResponse getStock(String stockName) {
        return stockService.getStock(stockName);
    }

    @Override
    @Transactional
    public void simulateMarketDay() {
        stockService.simulateMarketDay();
    }
}
