//package com.sk.skala.transaction.repository;
//
//import com.sk.skala.transaction.model.entity.Transaction;
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
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Repository
//public class TransactionRepositoryImpl implements TransactionRepository {
//
//    private static final String TRANSACTION_FILE = "data/transactions.txt";
//
//    private final List<Transaction> transactions = new ArrayList<>();
//
//    @PostConstruct
//    public void init() {
//        loadTransactions();
//    }
//
//    @PreDestroy
//    public void shutdown() {
//        saveTransactions();
//    }
//
//    @Override
//    public void loadTransactions() {
//        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_FILE))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                Transaction tx = parseLineToTransaction(line);
//                if (tx != null) transactions.add(tx);
//            }
//            log.info("거래 내역 {}건 로드 완료", transactions.size());
//        } catch (IOException e) {
//            log.warn("거래 내역 로드 실패 혹은 파일이 없음: {}", e.getMessage());
//        }
//    }
//
//    @Override
//    public void saveTransactions() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTION_FILE))) {
//            for (Transaction tx : transactions) {
//                // CSV 형식: transactionId,playerId,stockName,type,quantity,price,transactionTime
//                writer.write(tx.getTransactionId() + "," +
//                        tx.getPlayerId() + "," +
//                        tx.getStockName() + "," +
//                        tx.getType() + "," +
//                        tx.getQuantity() + "," +
//                        tx.getPrice() + "," +
//                        tx.getTransactionTime());
//                writer.newLine();
//            }
//            log.info("거래 내역 저장 완료");
//        } catch (IOException e) {
//            log.error("거래 내역 저장 중 오류 발생: {}", e.getMessage());
//        }
//    }
//
//    @Override
//    public void addTransaction(Transaction transaction) {
//        transactions.add(transaction);
//        saveTransactions();
//    }
//
//    @Override
//    public List<Transaction> getTransactionsForPlayer(String playerId) {
//        List<Transaction> result = new ArrayList<>();
//        for (Transaction tx : transactions)
//            if (tx.getPlayerId().equals(playerId)) result.add(tx);
//        return result;
//    }
//
//    @Override
//    public List<Transaction> getAllTransactions() {
//        return new ArrayList<>(transactions);
//    }
//
//    /**
//     * CSV 형식: transactionId,playerId,stockName,type,quantity,price,transactionTime
//     */
//    private Transaction parseLineToTransaction(String line) {
//        try {
//            String[] fields = line.split(",");
//            if (fields.length < 7) {
//                log.warn("거래 데이터 형식 오류: {}", line);
//                return null;
//            }
//            return Transaction.builder()
//                    .transactionId(fields[0].trim())
//                    .playerId(fields[1].trim())
//                    .stockName(fields[2].trim())
//                    .type(fields[3].trim())
//                    .quantity(Integer.parseInt(fields[4].trim()))
//                    .price(Integer.parseInt(fields[5].trim()))
//                    .transactionTime(LocalDateTime.parse(fields[6].trim()))
//                    .build();
//        } catch (Exception e) {
//            log.error("거래 파싱 중 오류: {} - line: {}", e.getMessage(), line);
//            return null;
//        }
//    }
//}
