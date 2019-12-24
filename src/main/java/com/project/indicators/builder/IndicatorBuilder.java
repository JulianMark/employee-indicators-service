package com.project.indicators.builder;

import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.IndicatorResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class IndicatorBuilder implements Function<IndicatorDTO, IndicatorResponse> {

    @Override
    public IndicatorResponse apply(IndicatorDTO indicatorDTO) {

        return new IndicatorResponse(indicatorDTO.getTotalDonations(),
                indicatorDTO.getTotalAmountDonations(),
                indicatorDTO.getCreditType(),
                indicatorDTO.getTotalProductiveHours(),
                indicatorDTO.getTotalNonProductiveHours(),
                obtainTotalAverageCatchment(indicatorDTO),
                obtainTotalAverageAmount(indicatorDTO),
                obtainTotalAverageCreditType(indicatorDTO),
                indicatorDTO.getErrorMessage());
    }

    private float obtainTotalAverageCatchment(IndicatorDTO indicatorDTO){
        return indicatorDTO.getTotalDonations() / indicatorDTO.getTotalProductiveHours();
    }

    private float obtainTotalAverageAmount(IndicatorDTO indicatorDTO){
        return indicatorDTO.getTotalAmountDonations()/ indicatorDTO.getTotalDonations();
    }

    private float obtainTotalAverageCreditType(IndicatorDTO indicatorDTO){
        return (indicatorDTO.getCreditType() / indicatorDTO.getTotalDonations()) * 100 ;
    }
}
