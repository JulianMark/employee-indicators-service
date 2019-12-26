package com.project.indicators.service;

import com.project.indicators.Utils.Utils;
import com.project.indicators.builder.IndicatorBuilder;
import com.project.indicators.mapper.*;
import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class IndicatorsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndicatorsService.class);
    private final HistoricalMapper historicalMapper;
    private final OSCMapper oscMapper;
    private final ActuallyMapper actuallyMapper;
    private final MonthlyMapper monthlyMapper;
    private final MonthlyOSCMapper monthlyOSCMapper;
    private final RangeMapper rangeMapper;
    private final IndicatorBuilder indicatorBuilder;
    private final Utils utils;

    @Autowired
    public IndicatorsService(HistoricalMapper historicalMapper, OSCMapper oscMapper, ActuallyMapper actuallyMapper, MonthlyOSCMapper monthlyOSCMapper, IndicatorBuilder indicatorBuilder, MonthlyMapper monthlyMapper, RangeMapper rangeMapper, Utils utils) {
        this.historicalMapper = historicalMapper;
        this.oscMapper = oscMapper;
        this.actuallyMapper = actuallyMapper;
        this.monthlyOSCMapper = monthlyOSCMapper;
        this.indicatorBuilder = indicatorBuilder;
        this.monthlyMapper = monthlyMapper;
        this.rangeMapper = rangeMapper;
        this.utils = utils;
    }

    @PostMapping(
            value = "employee/indicators/historical",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IndicatorResponse> obtainHistoricalIndicator (@RequestBody IndicatorRequest request){
        try{
            utils.validateRequest(request);
            utils.validateIdNumber(request.getIdEmployee());
            final IndicatorDTO indicatorDTO = historicalMapper.obtainHistorical(request);
            utils.validateIndicatorDTO(indicatorDTO);
            final IndicatorResponse response = indicatorBuilder.apply(indicatorDTO);
            LOGGER.info("Se obtuvo el indicador historico para el empleado con ID: "+request.getIdEmployee());
            return ResponseEntity.ok(response);
        }catch (IllegalArgumentException iae){
            LOGGER.warn("Los parametros ingresados son invalidos", iae);
            return ResponseEntity.badRequest().body(new IndicatorResponse(iae.getMessage()));
        }catch (Exception ex) {
            LOGGER.error("Ocurrio un error al intentar obtener los indicadores historicos para el empleado id {}"
                    ,request.getIdEmployee(), ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR).body(new IndicatorResponse(ex.getMessage()));
        }
    }

    @PostMapping(
            value = "employee/indicators/historical/osc",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IndicatorResponse> obtainHistoricalOSC (@RequestBody OSCRequest request){
        try {
            utils.validateRequest(request);
            utils.validateIdNumber(request.getIdEmployee());
            utils.validateIdNumber(request.getIdOSC());
            final IndicatorDTO indicatorDTO = oscMapper.obtainOSC(request);
            utils.validateIndicatorDTO(indicatorDTO);
            final IndicatorResponse response = indicatorBuilder.apply(indicatorDTO);
            LOGGER.info("Se obtuvo el indicador historico de la osc {} para el empleado con ID: {}", request.getIdOSC(), request.getIdEmployee());
            return ResponseEntity.ok(response);
        }catch (IllegalArgumentException iae){
            LOGGER.warn("Los parametros ingresados son invalidos", iae);
            return ResponseEntity.badRequest().body(new IndicatorResponse(iae.getMessage()));
        }catch (Exception ex) {
            LOGGER.error("Ocurrio un error al intentar obtener los indicadores historicos por OSC para el empleado id {}, OSC id {}"
                    ,request.getIdEmployee(),request.getIdOSC(), ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR).body(new IndicatorResponse(ex.getMessage()));
        }
    }

    @PostMapping(
            value = "employee/indicators/actually",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IndicatorResponse> obtainActualIndicator (@RequestBody IndicatorRequest request){
        try {
            utils.validateRequest(request);
            utils.validateIdNumber(request.getIdEmployee());
            final IndicatorDTO indicatorDTO = actuallyMapper.obtainActually(request);
            utils.validateIndicatorDTO(indicatorDTO);
            final IndicatorResponse response = indicatorBuilder.apply(indicatorDTO);
            LOGGER.info("Se obtuvo el indicador actual para el empleado con ID: "+request.getIdEmployee());
            return ResponseEntity.ok(response);
        }catch (IllegalArgumentException iae){
            LOGGER.warn("Los parametros ingresados son invalidos", iae);
            return ResponseEntity.badRequest().body(new IndicatorResponse(iae.getMessage()));
        }catch (Exception ex) {
            LOGGER.error("Ocurrio un error al intentar obtener los indicadores actuales para el empleado id {}"
                    ,request.getIdEmployee(), ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR).body(new IndicatorResponse(ex.getMessage()));
        }
    }

    @PostMapping(
            value = "employee/indicators/monthly",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IndicatorResponse> obtainMonthlyIndicator (@RequestBody MonthlyRequest request){
        try {
            utils.validateRequest(request);
            utils.validateIdNumber(request.getIdEmployee());
            utils.validateMonth(request.getMonthNumber());
            utils.validateYear(request.getYearNumber());
            final IndicatorDTO indicatorDTO = monthlyMapper.obtainMonthly(request);
            utils.validateIndicatorDTO(indicatorDTO);
            if (!utils.validatePropIndicatorDTO(indicatorDTO)){
                LOGGER.info("No se obtuvo el indicador mensual mes: {} anio: {} para el empleado con ID: {}"
                        ,request.getMonthNumber()
                        ,request.getYearNumber()
                        ,request.getIdEmployee());
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new IndicatorResponse("Indicadores no encontrados"));
            }
            final IndicatorResponse response = indicatorBuilder.apply(indicatorDTO);
            LOGGER.info("Se obtuvo el indicador mensual para el empleado con ID: "+request.getIdEmployee());
            return ResponseEntity.ok(response);
        }catch (IllegalArgumentException iae){
            LOGGER.warn("Los parametros ingresados son invalidos", iae);
            return ResponseEntity.badRequest().body(new IndicatorResponse(iae.getMessage()));
        }catch (Exception ex) {
            LOGGER.error("Ocurrio un error al intentar obtener los indicadores mensuales para el empleado id {}"
                    ,request.getIdEmployee(), ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR).body(new IndicatorResponse(ex.getMessage()));
        }
    }

    @PostMapping(
            value = "employee/indicators/monthly/osc",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IndicatorResponse> obtainMonthlyOSCIndicator (@RequestBody MonthlyRequest request){
        try {
            utils.validateRequest(request);
            utils.validateIdNumber(request.getIdEmployee());
            utils.validateMonth(request.getMonthNumber());
            utils.validateYear(request.getYearNumber());
            utils.validateIdNumber(request.getIdOSC());
            final IndicatorDTO indicatorDTO = monthlyOSCMapper.obtainMonthlyOSC(request);
            utils.validateIndicatorDTO(indicatorDTO);
            if (!utils.validatePropIndicatorDTO(indicatorDTO)){
                LOGGER.info("No se obtuvo el indicador mensual mes: {} anio: {} para el empleado con ID: {} OSC ID: {}"
                        ,request.getMonthNumber()
                        ,request.getYearNumber()
                        ,request.getIdEmployee()
                        ,request.getIdOSC());
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new IndicatorResponse("Indicadores no encontrados"));
            }
            final IndicatorResponse response = indicatorBuilder.apply(indicatorDTO);
            LOGGER.info("Se obtuvo el indicador mensual para el empleado con ID: {}, OSC ID: {}"
                    ,request.getIdEmployee()
                    ,request.getIdOSC());
            return ResponseEntity.ok(response);
        }catch (IllegalArgumentException iae){
            LOGGER.warn("Los parametros ingresados son invalidos", iae);
            return ResponseEntity.badRequest().body(new IndicatorResponse(iae.getMessage()));
        }catch (Exception ex) {
            LOGGER.error("Ocurrio un error al intentar obtener los indicadores mensuales para el empleado id {} OSC id {}"
                    ,request.getIdEmployee(),request.getIdOSC(), ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR).body(new IndicatorResponse(ex.getMessage()));
        }
    }

    @PostMapping(
            value = "employee/indicators/range",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IndicatorResponse> obtainRangeIndicator (@RequestBody RangeRequest request){
        try {
            utils.validateRequest(request);
            utils.validateIdNumber(request.getIdEmployee());
            utils.isDateValid(request.getInitialDate());
            utils.isDateValid(request.getFinalDate());
            final IndicatorDTO indicatorDTO = rangeMapper.obtainRange(request);
            utils.validateIndicatorDTO(indicatorDTO);
            final IndicatorResponse response = indicatorBuilder.apply(indicatorDTO);
            LOGGER.info("Se obtuvo el indicador por rango desde {} hasta {} para el empleado con ID: "
                    ,request.getInitialDate()
                    ,request.getFinalDate()
                    ,request.getIdEmployee());
            return ResponseEntity.ok(response);
        }catch (IllegalArgumentException iae){
            LOGGER.warn("Los parametros ingresados son invalidos", iae);
            return ResponseEntity.badRequest().body(new IndicatorResponse(iae.getMessage()));
        }catch (Exception ex) {
            LOGGER.error("Ocurrio un error al intentar obtener los indicadores por rango desde {} hasta {} para el empleado id {}"
                    ,request.getInitialDate()
                    ,request.getFinalDate()
                    ,request.getIdEmployee(), ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR).body(new IndicatorResponse(ex.getMessage()));
        }
    }
}
