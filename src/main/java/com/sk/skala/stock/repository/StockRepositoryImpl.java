//package com.sk.skala.stock.repository;
//
//import com.sk.skala.stock.model.entity.Stock;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Repository
//public class StockRepositoryImpl implements StockRepository {
//
//    private static final String STOCK_FILE = "data/stocks.txt";
//    private final List<Stock> stockList = new ArrayList<>();
//
//    @PostConstruct
//    public void init() {
//        loadStockList();
//    }
//
//    @PreDestroy
//    public void shutdown() {
//        saveStockList();
//    }
//
//    @Override
//    public void loadStockList() {
//        try (BufferedReader reader = new BufferedReader(new FileReader(STOCK_FILE))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                Stock stock = parseLineToStock(line);
//                if (stock != null) {
//                    stockList.add(stock);
//                }
//            }
//            log.info("총 {}개의 주식 정보를 파일에서 로드했습니다.", stockList.size());
//        } catch (IOException e) {
//            log.warn("주식 정보를 로드하는 데 실패하거나 파일이 존재하지 않습니다: {}. 기본 데이터를 사용합니다.", e.getMessage());
//            // 파일이 없거나 오류 발생 시 기본 주식 정보 추가 (sector 포함)
//            stockList.add(new Stock("TechCorp", "TC", 100, "TECH"));
//            stockList.add(new Stock("GreenEnergy", "GE", 80, "ENERGY"));
//            stockList.add(new Stock("HealthPlus", "HP", 120, "HEALTH"));
//        }
//    }
//
//    @Override
//    public void saveStockList() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STOCK_FILE))) {
//            for (Stock stock : stockList) {
//                // CSV 형식: stockName,tickerSymbol,stockPrice,sector
//                String ticker = stock.getTickerSymbol() == null ? "" : stock.getTickerSymbol();
//                String sector = stock.getSector() == null ? "" : stock.getSector();
//                writer.write(stock.getStockName() + "," + ticker + "," + stock.getStockPrice() + "," + sector);
//                writer.newLine();
//            }
//            log.info("주식 정보를 파일에 저장했습니다.");
//        } catch (IOException e) {
//            log.error("주식 정보를 저장하는 중 오류 발생: {}", e.getMessage());
//        }
//    }
//
//    @Override
//    public List<Stock> getStockList() {
//        return new ArrayList<>(stockList); // 복사본 반환
//    }
//
//    @Override
//    public Stock findStock(int index) {
//        if (index >= 0 && index < stockList.size())
//            return stockList.get(index);
//        return null;
//    }
//
//    @Override
//    public Stock findStock(String stockName) {
//        return stockList.stream()
//                .filter(s -> s.getStockName().equalsIgnoreCase(stockName))
//                .findFirst()
//                .orElse(null);
//    }
//
//    /**
//     * CSV 형식: stockName,tickerSymbol,stockPrice,sector
//     */
//    private Stock parseLineToStock(String line) {
//        try {
//            String[] fields = line.split(",");
//            if (fields.length < 3) {
//                log.warn("주식 데이터 형식 오류: {}", line);
//                return null;
//            }
//            String name = fields[0].trim();
//            String ticker = fields[1].trim();
//            int price = Integer.parseInt(fields[2].trim());
//            String sector = fields.length >= 4 ? fields[3].trim() : "";
//            return new Stock(name, ticker, price, sector);
//        } catch (Exception e) {
//            log.error("주식 데이터를 파싱하는 중 오류 발생: {} - line: {}", e.getMessage(), line);
//            return null;
//        }
//    }
//}
