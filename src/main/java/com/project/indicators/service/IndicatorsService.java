package com.project.indicators.service;

import com.project.indicators.builder.HistoricalBuilder;
import com.project.indicators.mapper.HistoricalMapper;
import com.project.indicators.mapper.OSCMapper;
import com.project.indicators.model.OSC;
import com.project.indicators.model.dto.HistoricalDTO;
import com.project.indicators.service.http.HistoricalRequest;
import com.project.indicators.service.http.HistoricalResponse;
import com.project.indicators.service.http.OSCsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IndicatorsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndicatorsService.class);
    private final OSCMapper oscMapper;
    private final HistoricalMapper historicalMapper;
    private final HistoricalBuilder historicalBuilder;

    @Autowired
    public IndicatorsService(OSCMapper oscMapper, HistoricalMapper historicalMapper, HistoricalBuilder historicalBuilder) {
        this.oscMapper = oscMapper;
        this.historicalMapper = historicalMapper;
        this.historicalBuilder = historicalBuilder;
    }

    @GetMapping(
        value = "employee/indicators/osc/{idEmployee}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OSCsResponse> obtainOSCs(@PathVariable Integer idEmployee) {
        final List<OSC> oscList = oscMapper.obtainOSCs(idEmployee);
        LOGGER.info("Se obtuvieron las oscs para el empleado "+idEmployee);
        return ResponseEntity.ok( new OSCsResponse(oscList));

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
