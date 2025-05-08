package com.sk.skala.stock.config;

import com.sk.skala.stock.model.entity.Stock;
import com.sk.skala.stock.model.entity.StockPriceHistory;
import com.sk.skala.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StockDataInitializer implements CommandLineRunner {

    private final StockRepository stockRepository;

    @Override
    public void run(String... args) throws Exception {
        if (stockRepository.count() == 0) {
            List<Stock> defaultStocks = Arrays.asList(
                    Stock.builder()
                            .stockName("TechCorp")
                            .tickerSymbol("TC")
                            .stockPrice(100)
                            .sector("TECH")
                            .stockPriceHistory(StockPriceHistory.builder().build())
                            .build(),
                    Stock.builder()
                            .stockName("GreenEnergy")
                            .tickerSymbol("GE")
                            .stockPrice(80)
                            .sector("ENERGY")
                            .stockPriceHistory(StockPriceHistory.builder().build())
                            .build(),
                    Stock.builder()
                            .stockName("HealthPlus")
                            .tickerSymbol("HP")
                            .stockPrice(120)
                            .sector("HEALTH")
                            .stockPriceHistory(StockPriceHistory.builder().build())
                            .build()
            );
            stockRepository.saveAll(defaultStocks);
            System.out.println("기본 주식 데이터가 초기화되었습니다.");
        } else {
            System.out.println("주식 데이터가 이미 존재합니다.");
        }
    }
}
