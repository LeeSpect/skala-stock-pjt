package com.sk.skala.stock.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MarketSimulationEventListener {

    // 바뀐 파일: MarketSimulationEvent, StockServiceImpl, MarketSimulationEventListener
    @EventListener // 이벤트 리스너: MarketSimulationEvent 발생 시 호출
    public void handleMarketSimulationEvent(MarketSimulationEvent event) {
        log.info("시장 시뮬레이션 이벤트 발생: {}개 종목의 주가가 업데이트 되었습니다.", event.getUpdatedStocks().size());
    }
}
