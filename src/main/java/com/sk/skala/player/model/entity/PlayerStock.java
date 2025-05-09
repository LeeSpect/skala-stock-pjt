package com.sk.skala.player.model.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import com.sk.skala.stock.model.entity.Stock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 플레이어가 소유한 주식 정보
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "player_stock-84")
public class PlayerStock {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "player_stock_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "player_id", nullable = false, referencedColumnName = "player_id")
    private Player player;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Builder.Default
    @Column(name = "stock_quantity")
    private int stockQuantity = 0; // 플레이어 소유 주식 수량

    public PlayerStock(Stock stock, int quantity) {
        this.stock = stock;
        this.stockQuantity = quantity;
    }

    @Override
    public String toString() {
        // super ex. "TechCorp(TC):100"
        return super.toString() + ":" + stockQuantity;
    }
}
