package com.project.indicators.model.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class IndicatorDTO {

    private Float totalDonations;
    private Float totalAmountDonations;
    private Float creditType;
    private Float totalProductiveHours;
    private Float totalNonProductiveHours;
    private String errorMessage;
}
