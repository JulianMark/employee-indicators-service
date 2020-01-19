package com.project.indicators.builder;

import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.IndicatorResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class IndicatorBuilder implements Function<IndicatorDTO, IndicatorResponse> {

    @Override
    public IndicatorResponse apply(IndicatorDTO indicatorDTO) {

        final Float TOTAL_DONATIONS = numericConverter(indicatorDTO.getTotalDonations());
        final Float TOTAL_AMOUNT_DONATIONS = numericConverter(indicatorDTO.getTotalAmountDonations());
        final Float CREDIT_TYPE = numericConverter(indicatorDTO.getCreditType());
        final Float PRODUCTIVE_HOURS = numericConverter(indicatorDTO.getTotalProductiveHours());
        final Float NON_PRODUCTIVE_HOURS = numericConverter(indicatorDTO.getTotalNonProductiveHours());


        return new IndicatorResponse(TOTAL_DONATIONS,
                TOTAL_AMOUNT_DONATIONS,
                CREDIT_TYPE,
                PRODUCTIVE_HOURS,
                NON_PRODUCTIVE_HOURS,
                obtainTotalAverageCatchment(TOTAL_DONATIONS,PRODUCTIVE_HOURS),
                obtainTotalAverageAmount(TOTAL_AMOUNT_DONATIONS, TOTAL_DONATIONS),
                obtainTotalAverageCreditType(CREDIT_TYPE,TOTAL_DONATIONS),
                indicatorDTO.getErrorMessage());
    }

    public Float numericConverter(Float num) {
        return ( num == null) ? 0f : num;
    }

    public Float obtainTotalAverageCatchment(Float totalDonations, Float totalProductiveHours){
        return totalDonations / totalProductiveHours;
    }

    public Float obtainTotalAverageAmount(Float totalAmountDonations, Float totalDonations){
        return totalAmountDonations / totalDonations;
    }

    public Float obtainTotalAverageCreditType(Float creditType, Float totalDonations){
        return (creditType / totalDonations) * 100 ;
    }

}
