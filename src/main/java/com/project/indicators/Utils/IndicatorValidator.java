package com.project.indicators.Utils;

import com.project.indicators.builder.IndicatorBuilder;
import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.IndicatorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class IndicatorValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndicatorValidator.class);
    private final IndicatorBuilder indicatorBuilder;

    public IndicatorValidator(IndicatorBuilder indicatorBuilder) {
        this.indicatorBuilder = indicatorBuilder;
    }

    public Function<IndicatorDTO, ResponseEntity<IndicatorResponse>> obtainIndicatorValidator(){
        return this::obtainIndicator;
    }

    public ResponseEntity<IndicatorResponse> obtainIndicator(IndicatorDTO indicatorDTO) {
        final IndicatorResponse response = indicatorBuilder.apply(indicatorDTO);
        LOGGER.info("Indicators for the employee were obtained");
        return ResponseEntity.ok(response);
    }
    
    public Supplier<ResponseEntity<IndicatorResponse>> obtainEmptyIndicator(){
        return () -> {
            LOGGER.warn("There are no indicators with the established search parameters");
            return ResponseEntity.noContent().build();
        };
    }
}
