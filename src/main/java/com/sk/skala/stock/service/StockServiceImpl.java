package com.sk.skala.stock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sk.skala.stock.event.MarketSimulationEvent;
import com.sk.skala.stock.model.dto.response.StockResponse;
import com.sk.skala.stock.model.entity.Stock;
import com.sk.skala.stock.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final Random random = new Random();

    @Override
    public List<StockResponse> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        List<StockResponse> responses = new ArrayList<>();
        for (Stock stock : stocks)
            responses.add(StockResponse.from(stock));
        return responses;
    }

    @Override
    public StockResponse getStock(String stockName) {
        Stock stock = stockRepository.findByStockNameIgnoreCase(stockName);
        if (stock == null) throw new RuntimeException("주식을 찾을 수 없습니다.");
        return StockResponse.from(stock);
    }

    @Override
    @Transactional
    @Scheduled(fixedRate = 3000) // 3초마다 실행
    public void simulateMarketDay() {
        List<Stock> stocks = stockRepository.findAll();
        for (Stock stock : stocks) {
            double changePercent = (random.nextDouble() * 20) - 8; // -8% ~ 12%
            int oldPrice = stock.getStockPrice();
            int newPrice = (int) Math.max(1, oldPrice + oldPrice * changePercent / 100);
            stock.updateStockPrice(newPrice);
        }
        stockRepository.saveAll(stocks);
        // 이벤트 발생
        eventPublisher.publishEvent(new MarketSimulationEvent(this, stocks));
    }
}
