package com.sk.skala.stock.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 주식 생성(등록) 요청 DTO
 */
@Getter
@Setter
public class StockCreateRequest {

    @NotBlank(message = "주식 이름은 필수입니다.")
    private String stockName;

    private String tickerSymbol;

    @Min(value = 1, message = "주가(최소 1원 이상)")
    private int initialPrice;

    private String sector;
}
