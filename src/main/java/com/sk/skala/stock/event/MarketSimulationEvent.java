package com.sk.skala.stock.event;

import com.sk.skala.stock.model.entity.Stock;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class MarketSimulationEvent extends ApplicationEvent {

    private final List<Stock> updatedStocks;

    public MarketSimulationEvent(Object source, List<Stock> updatedStocks) {
        super(source);
        this.updatedStocks = updatedStocks;
    }
}
