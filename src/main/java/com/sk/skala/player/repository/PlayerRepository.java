package com.sk.skala.player.repository;

import com.sk.skala.player.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, String> {

    /**
     * 플레이어 존재 유무 확인
     * @param playerId 플레이어 아이디(문자열)
     * @return 플레이어 존재 유무(true: 존재, false: 미존재)
     */
    boolean existsByPlayerId(String playerId);

    /**
     * 플레이어 아이디로 플레이어 조회
     * @param playerId 플레이어 아이디(문자열)
     * @return 플레이어 객체
     */
    Player findByPlayerId(String playerId);
}