package com.project.indicators.service.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IndicatorResponse {

    private Float totalDonations;
    private Float totalAmountDonations;
    private Float creditType;
    private Float totalProductiveHours;
    private Float totalNonProductiveHours;
    private Float totalAverageCatchment;
    private Float totalAverageAmount;
    private Float totalAverageCreditType;
    private String errorMessage;

    public IndicatorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
