package com.project.indicators.service;

import com.project.indicators.builder.IndicatorBuilder;
import com.project.indicators.mapper.HistoricalMapper;
import com.project.indicators.mapper.OSCMapper;
import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.HistoricalRequest;
import com.project.indicators.service.http.IndicatorResponse;
import com.project.indicators.service.http.OSCRequest;
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
    private final OSCMapper oscMapper;
    private final IndicatorBuilder indicatorBuilder;

    @Autowired
    public IndicatorsService(HistoricalMapper historicalMapper, OSCMapper oscMapper, IndicatorBuilder indicatorBuilder) {
        this.historicalMapper = historicalMapper;
        this.oscMapper = oscMapper;
        this.indicatorBuilder = indicatorBuilder;
    }

    @PostMapping(
            value = "employee/indicators/historical",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IndicatorResponse> obtainHistoricalIndicator (@RequestBody HistoricalRequest request){
        final IndicatorDTO indicatorDTO = historicalMapper.obtainHistorical(request);
        final IndicatorResponse response = indicatorBuilder.apply(indicatorDTO);
        LOGGER.info("Se obtuvo el indicador historico para el empleado con ID: "+request.getIdEmployee());
        return ResponseEntity.ok(response);
    }

    @PostMapping(
            value = "employee/indicators/historical/osc",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IndicatorResponse> obtainHistoricalOSC (@RequestBody OSCRequest request){
        final IndicatorDTO indicatorDTO = oscMapper.obtainOSC(request);
        final IndicatorResponse response = indicatorBuilder.apply(indicatorDTO);
        LOGGER.info("Se obtuvo el indicador historico de la osc {} para el empleado con ID: {}", request.getIdOSC(), request.getIdEmployee());
        return ResponseEntity.ok(response);
    }

}
