//package com.sk.skala.player.repository;
//
//import com.sk.skala.player.model.entity.Player;
//import com.sk.skala.player.model.entity.PlayerRank;
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
//public class PlayerRepositoryImpl implements PlayerRepository {
//
//    private static final String PLAYER_FILE = "data/players.txt";
//
//    // 메모리 내 플레이어 목록
//    private final List<Player> playerList = new ArrayList<>();
//
//    @PostConstruct // 빈 생성 시 파일에서 플레이어 정보 로드
//    public void init() {
//        loadPlayerList();
//    }
//
//    @PreDestroy // 빈 소멸 시 플레이어 정보 저장
//    public void shutdown() {
//        savePlayerList();
//    }
//
//    @Override
//    public void loadPlayerList() {
//        try (BufferedReader reader = new BufferedReader(new FileReader(PLAYER_FILE))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                Player player = parseLineToPlayer(line);
//                if (player != null) playerList.add(player);
//            }
//            log.info("플레이어 {}명 로드 완료", playerList.size());
//        } catch (IOException e) {
//            log.warn("플레이어 정보 로드 실패 혹은 파일이 존재하지 않음: {}", e.getMessage());
//        }
//    }
//
//    @Override
//    public void savePlayerList() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLAYER_FILE))) {
//            for (Player player : playerList) {
//                // CSV 형식: playerId,playerName,playerMoney,creationTime,lastLoginTime,rank,playerStocks(옵션)
//                StringBuilder sb = new StringBuilder();
//                sb.append(player.getPlayerId()).append(",")
//                        .append(player.getPlayerName() == null ? "" : player.getPlayerName()).append(",")
//                        .append(player.getPlayerMoney()).append(",")
//                        .append(player.getCreationTime()).append(",")
//                        .append(player.getLastLoginTime()).append(",")
//                        .append(player.getRank());
//                // 보유 주식은 간단히 toString()으로 표기
//                if (player.getPlayerStocks() != null && !player.getPlayerStocks().isEmpty()) {
//                    sb.append(",");
//                    for (int i = 0; i < player.getPlayerStocks().size(); i++) {
//                        if(i > 0) sb.append("|");
//                        sb.append(player.getPlayerStocks().get(i).toString());
//                    }
//                }
//                writer.write(sb.toString());
//                writer.newLine();
//            }
//            log.info("플레이어 정보 저장 완료");
//        } catch (IOException e) {
//            log.error("플레이어 정보 저장 중 오류 발생: {}", e.getMessage());
//        }
//    }
//
//    @Override
//    public Player findPlayer(String playerId) {
//        return playerList.stream()
//                .filter(p -> p.getPlayerId().equals(playerId)) // playerId로 플레이어 찾기
//                .findFirst()
//                .orElse(null);
//    }
//
//    @Override
//    public void addPlayer(Player player) {
//        playerList.add(player);
//        savePlayerList();
//    }
//
//    @Override
//    public List<Player> getAllPlayers() {
//        return new ArrayList<>(playerList);
//    }
//
//    /**
//     * CSV 형식의 한 줄을 Player 객체로 변환
//     * 형식: playerId,playerName,playerMoney,creationTime,lastLoginTime,rank,[주식목록]
//     */
//    private Player parseLineToPlayer(String line) {
//        try {
//            String[] fields = line.split(",", 7);
//            if (fields.length < 6) {
//                log.warn("플레이어 데이터 형식 오류: {}", line);
//                return null;
//            }
//            return Player.builder()
//                    .playerId(fields[0])
//                    .playerName(fields[1])
//                    .playerMoney(Integer.parseInt(fields[2])) // 빌더 디폴트 해서, 이 뒤에 더 없어도 됨
//                    .build();
//        } catch (Exception e) {
//            log.error("플레이어 파싱 중 오류: {} - line: {}", e.getMessage(), line);
//            return null;
//        }
//    }
//}
