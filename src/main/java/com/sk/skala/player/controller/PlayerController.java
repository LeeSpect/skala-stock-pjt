package com.sk.skala.player.controller;

import com.sk.skala.player.facade.PlayerFacade;
import com.sk.skala.player.model.dto.request.PlayerCreateRequest;
import com.sk.skala.player.model.dto.request.PlayerUpdateRequest;
import com.sk.skala.player.model.dto.response.PortfolioSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players")
public class PlayerController implements PlayerControllerSpecification {

    private final PlayerFacade playerFacade;

    @PostMapping
    public ResponseEntity<?> createPlayer(@Validated @RequestBody PlayerCreateRequest request) {
        return ResponseEntity.ok(playerFacade.createPlayer(request));
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<?> getPlayer(@PathVariable String playerId) {
        return ResponseEntity.ok(playerFacade.getPlayer(playerId));
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<?> updatePlayer(@PathVariable String playerId,
                                          @Validated @RequestBody PlayerUpdateRequest request) {
        return ResponseEntity.ok(playerFacade.updatePlayer(playerId, request));
    }

    @PostMapping("/{playerId}/buy")
    public ResponseEntity<?> buyStock(@PathVariable String playerId,
                                      @RequestParam String stockName,
                                      @RequestParam int quantity) {
        return ResponseEntity.ok(playerFacade.buyStock(playerId, stockName, quantity));
    }

    @PostMapping("/{playerId}/sell")
    public ResponseEntity<?> sellStock(@PathVariable String playerId,
                                       @RequestParam String stockName,
                                       @RequestParam int quantity) {
        return ResponseEntity.ok(playerFacade.sellStock(playerId, stockName, quantity));
    }

    @GetMapping("/{playerId}/portfolio")
    public ResponseEntity<PortfolioSummaryResponse> getPortfolioSummary(@PathVariable String playerId) {
        return ResponseEntity.ok(playerFacade.getPortfolioSummary(playerId));
    }
}
