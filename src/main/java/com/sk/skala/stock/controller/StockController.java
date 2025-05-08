package com.sk.skala.stock.controller;

import com.sk.skala.stock.facade.StockFacade;
import com.sk.skala.stock.model.dto.response.StockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stocks")
public class StockController implements StockControllerSpecification {

    private final StockFacade stockFacade;

    @GetMapping
    public ResponseEntity<?> getAllStocks() {
        return ResponseEntity.ok(stockFacade.getAllStocks());
    }

    @GetMapping("/{stockName}")
    public ResponseEntity<?> getStock(@PathVariable String stockName) {
        return ResponseEntity.ok(stockFacade.getStock(stockName));
    }

    @PostMapping("/simulate")
    public ResponseEntity<?> simulateMarketDay() {
        stockFacade.simulateMarketDay();
        return ResponseEntity.ok("시뮬레이션 완료");
    }
}
