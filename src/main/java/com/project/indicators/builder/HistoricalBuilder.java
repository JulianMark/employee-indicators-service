package com.project.indicators.builder;

import com.project.indicators.model.dto.HistoricalDTO;
import com.project.indicators.service.http.HistoricalResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class HistoricalBuilder implements Function<HistoricalDTO, HistoricalResponse> {

    @Override
    public HistoricalResponse apply(HistoricalDTO historicalDTO) {

        return new HistoricalResponse(historicalDTO.getTotalDonations(),
                historicalDTO.getTotalAmountDonations(),
                historicalDTO.getCreditType(),
                historicalDTO.getTotalProductiveHours(),
                historicalDTO.getTotalNonProductiveHours(),
                obtainTotalAverageCatchment(historicalDTO),
                obtainTotalAverageAmount(historicalDTO),
                obtainTotalAverageCreditType(historicalDTO),
                historicalDTO.getErrorMessage());
    }

    private float obtainTotalAverageCatchment(HistoricalDTO historicalDTO){
        return historicalDTO.getTotalDonations() / historicalDTO.getTotalProductiveHours();
    }

    private float obtainTotalAverageAmount(HistoricalDTO historicalDTO){
        return historicalDTO.getTotalAmountDonations()/historicalDTO.getTotalDonations();
    }

    private float obtainTotalAverageCreditType(HistoricalDTO historicalDTO){
        return (historicalDTO.getCreditType() / historicalDTO.getTotalDonations()) * 100 ;
    }
}
