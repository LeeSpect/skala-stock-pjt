package com.sk.skala.player.model.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * 플레이어 정보 변경(이름, 자금 등) 요청 DTO
 * - 보통 ID를 PathVariable 등으로 전달받고,
 *   이 DTO에는 변경할 필드만 두고 개발했음
 */
@Getter
@Setter
public class PlayerUpdateRequest {

    private String playerName;

    @Min(value = 0, message = "플레이어 자금은 음수가 될 수 없습니다.")
    private int playerMoney;
}
