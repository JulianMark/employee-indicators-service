package com.project.indicators.builder;

import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.IndicatorResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class IndicatorBuilder implements Function<IndicatorDTO, IndicatorResponse> {

    @Override
    public IndicatorResponse apply(IndicatorDTO indicatorDTO) {

        return new IndicatorResponse(numericConverter(indicatorDTO.getTotalDonations()),
                numericConverter(indicatorDTO.getTotalAmountDonations()),
                numericConverter(indicatorDTO.getCreditType()),
                numericConverter(indicatorDTO.getTotalProductiveHours()),
                numericConverter(indicatorDTO.getTotalNonProductiveHours()),
                obtainTotalAverageCatchment(indicatorDTO),
                obtainTotalAverageAmount(indicatorDTO),
                obtainTotalAverageCreditType(indicatorDTO),
                indicatorDTO.getErrorMessage());
    }

    public Float numericConverter(Float num) {
        return ( num == null) ? 0f : num;
    }

    public Float obtainTotalAverageCatchment(IndicatorDTO indicatorDTO){
        return numericConverter(indicatorDTO.getTotalDonations()) / numericConverter(indicatorDTO.getTotalProductiveHours());
    }

    public Float obtainTotalAverageAmount(IndicatorDTO indicatorDTO){
        return numericConverter(indicatorDTO.getTotalAmountDonations()) / numericConverter(indicatorDTO.getTotalDonations());
    }

    public Float obtainTotalAverageCreditType(IndicatorDTO indicatorDTO){
        return (numericConverter(indicatorDTO.getCreditType()) / numericConverter(indicatorDTO.getTotalDonations())) * 100 ;
    }

}
