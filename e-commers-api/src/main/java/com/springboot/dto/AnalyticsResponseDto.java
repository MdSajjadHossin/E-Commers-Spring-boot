package com.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class AnalyticsResponseDto {
    private Long placed;
    private Long shipped;
    private Long delivered;
    private Long currentMontOrder;
    private Long previousMonthOrder;
    private Long currentMontEarning;
    private Long previousMontEarning;

}
