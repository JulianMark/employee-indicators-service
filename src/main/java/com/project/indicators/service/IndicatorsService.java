package com.project.indicators.service;

import com.project.indicators.builder.HistoricalBuilder;
import com.project.indicators.mapper.HistoricalMapper;
import com.project.indicators.model.dto.HistoricalDTO;
import com.project.indicators.service.http.HistoricalRequest;
import com.project.indicators.service.http.HistoricalResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class IndicatorsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndicatorsService.class);
    private final HistoricalMapper historicalMapper;
    private final HistoricalBuilder historicalBuilder;

    @Autowired
    public IndicatorsService( HistoricalMapper historicalMapper, HistoricalBuilder historicalBuilder) {
        this.historicalMapper = historicalMapper;
        this.historicalBuilder = historicalBuilder;
    }

    @PostMapping(
            value = "employee/indicators/historical",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HistoricalResponse> obtainHistoricalIndicator (@RequestBody HistoricalRequest request){
        final HistoricalDTO historicalDTO = historicalMapper.obtainHistorical(request);
        final HistoricalResponse response = historicalBuilder.apply(historicalDTO);
        LOGGER.info("Se obtuvo el indicador historico para el empleado con ID: "+request.getIdEmployee());
        return ResponseEntity.ok(response);
    }

}
