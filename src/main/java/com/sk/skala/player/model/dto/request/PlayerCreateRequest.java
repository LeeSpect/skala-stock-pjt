package com.sk.skala.player.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 플레이어 생성 요청 DTO
 */
@Getter
@Setter
@Builder
public class PlayerCreateRequest {

    //- `@NotNull`: null이 아닐 경우 통과
    //- `@NotEmpty`: null 또는 빈 문자열이 아닐 경우 통과
    //- `@NotBlank`: null, 빈 문자열, 공백만으로 이루어진 문자열이 아닐 경우 통과

    @NotBlank(message = "플레이어 ID는 필수입니다.")
    private String playerId;

    @NotBlank(message = "플레이어 이름은 필수입니다.")
    private String playerName;

    @Min(value = 0, message = "초기 투자금은 음수가 될 수 없습니다.")
    private int initialMoney;
}
