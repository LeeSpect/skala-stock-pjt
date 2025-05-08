package com.sk.skala.player.repository;

import com.sk.skala.player.model.entity.PlayerStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerStockRepository extends JpaRepository<PlayerStock, Long> {
}
