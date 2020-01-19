package com.project.indicators.service;

import com.project.indicators.Utils.IndicatorValidator;
import com.project.indicators.builder.IndicatorBuilder;
import com.project.indicators.mapper.*;
import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.project.indicators.Utils.Utils.*;


@RestController
@Api(value="Indicators employee WS", produces = MediaType.APPLICATION_JSON_VALUE)
public class IndicatorsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndicatorsService.class);
    private final HistoricalMapper historicalMapper;
    private final OSCMapper oscMapper;
    private final ActuallyMapper actuallyMapper;
    private final MonthlyMapper monthlyMapper;
    private final MonthlyOSCMapper monthlyOSCMapper;
    private final RangeMapper rangeMapper;
    private final CampaignMapper campaignMapper;
    private final IndicatorBuilder indicatorBuilder;
    private final IndicatorValidator indicatorValidator;

    @Autowired
    public IndicatorsService(HistoricalMapper historicalMapper, OSCMapper oscMapper, ActuallyMapper actuallyMapper, MonthlyOSCMapper monthlyOSCMapper, IndicatorBuilder indicatorBuilder, MonthlyMapper monthlyMapper, RangeMapper rangeMapper, CampaignMapper campaignMapper, IndicatorValidator indicatorValidator) {
        this.historicalMapper = historicalMapper;
        this.oscMapper = oscMapper;
        this.actuallyMapper = actuallyMapper;
        this.monthlyOSCMapper = monthlyOSCMapper;
        this.indicatorBuilder = indicatorBuilder;
        this.monthlyMapper = monthlyMapper;
        this.rangeMapper = rangeMapper;
        this.campaignMapper = campaignMapper;
        this.indicatorValidator = indicatorValidator;
    }

    @PostMapping(
            value = "employee/indicators/historical",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Obtener indicadores historicos del empleado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Se obtienen los indicadores historicos del empleado", response = IndicatorResponse.class),
            @ApiResponse(code = 204, message = "El empleado ingresado es incorrecto", response = IndicatorResponse.class),
            @ApiResponse(code = 400, message = "Argumentos inválidos", response = IndicatorResponse.class),
            @ApiResponse(code = 500, message = "Error inesperado del servicio web", response = IndicatorResponse.class)
    })
    public ResponseEntity<IndicatorResponse> obtainHistoricalIndicator (@RequestBody IndicatorRequest request){
        try{
            validateRequest(request);
            validateIdNumber(request.getIdEmployee());
            return Optional.ofNullable(historicalMapper.obtainHistorical(request))
                    .map(indicatorValidator.obtainIndicatorValidator())
                    .orElseGet(indicatorValidator.obtainEmptyIndicator());
        }catch (IllegalArgumentException iae){
            LOGGER.warn("Parameters entered are invalid", iae);
            return ResponseEntity.badRequest().body(new IndicatorResponse(iae.getMessage()));
        }catch (Exception ex) {
            LOGGER.error("An error occurred while trying to get historical indicators for employee id {}"
                    ,request.getIdEmployee(), ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR).body(new IndicatorResponse(ex.getMessage()));
        }
    }

    @PostMapping(
            value = "employee/indicators/historical/osc",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Obtener indicadores historicos por OSC del empleado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Se obtienen los indicadores historicos por OSC el empleado", response = IndicatorResponse.class),
            @ApiResponse(code = 204, message = "El dato ingresado es incorrecto", response = IndicatorResponse.class),
            @ApiResponse(code = 400, message = "Argumentos inválidos", response = IndicatorResponse.class),
            @ApiResponse(code = 500, message = "Error inesperado del servicio web", response = IndicatorResponse.class)
    })
    public ResponseEntity<IndicatorResponse> obtainHistoricalOSC (@RequestBody OSCRequest request){
        try {
            validateRequest(request);
            validateIdNumber(request.getIdEmployee());
            validateIdNumber(request.getIdOSC());
            final IndicatorDTO indicatorDTO = oscMapper.obtainOSC(request);
            if (indicatorDTO == null){
                LOGGER.info("No se obtubieron indicadores historicos de la OSC para el emmpleado con ID: "+request.getIdEmployee());
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new IndicatorResponse("Empleado o indicadores no encontrados"));
            }
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
    @ApiOperation("Obtener indicadores actuales del empleado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Se obtienen los indicadores actuales el empleado", response = IndicatorResponse.class),
            @ApiResponse(code = 204, message = "El empleado ingresado es incorrecto", response = IndicatorResponse.class),
            @ApiResponse(code = 400, message = "Argumentos inválidos", response = IndicatorResponse.class),
            @ApiResponse(code = 500, message = "Error inesperado del servicio web", response = IndicatorResponse.class)
    })
    public ResponseEntity<IndicatorResponse> obtainActualIndicator (@RequestBody IndicatorRequest request){
        try {
            validateRequest(request);
            validateIdNumber(request.getIdEmployee());
            final IndicatorDTO indicatorDTO = actuallyMapper.obtainActually(request);
            if (indicatorDTO == null){
                LOGGER.info("No se obtubieron indicadores actuales para el emmpleado con ID: "+request.getIdEmployee());
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new IndicatorResponse("Empleado o indicadores actuales no encontrados"));
            }
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
    @ApiOperation("Obtener indicadores mensuales del empleado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Se obtienen los indicadores mensuales el empleado", response = IndicatorResponse.class),
            @ApiResponse(code = 204, message = "El dato ingresado es incorrecto", response = IndicatorResponse.class),
            @ApiResponse(code = 400, message = "Argumentos inválidos", response = IndicatorResponse.class),
            @ApiResponse(code = 500, message = "Error inesperado del servicio web", response = IndicatorResponse.class)
    })
    public ResponseEntity<IndicatorResponse> obtainMonthlyIndicator (@RequestBody MonthlyRequest request){
        try {
            validateRequest(request);
            validateIdNumber(request.getIdEmployee());
            validateMonth(request.getMonthNumber());
            validateYear(request.getYearNumber());
            final IndicatorDTO indicatorDTO = monthlyMapper.obtainMonthly(request);
            if (indicatorDTO == null){
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
    @ApiOperation("Obtener indicadores mensuales por OSC del empleado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Se obtienen los indicadores mensuales por OSC el empleado", response = IndicatorResponse.class),
            @ApiResponse(code = 204, message = "El dato ingresado es incorrecto", response = IndicatorResponse.class),
            @ApiResponse(code = 400, message = "Argumentos inválidos", response = IndicatorResponse.class),
            @ApiResponse(code = 500, message = "Error inesperado del servicio web", response = IndicatorResponse.class)
    })
    public ResponseEntity<IndicatorResponse> obtainMonthlyOSCIndicator (@RequestBody MonthlyRequest request){
        try {
            validateRequest(request);
            validateIdNumber(request.getIdEmployee());
            validateMonth(request.getMonthNumber());
            validateYear(request.getYearNumber());
            validateIdNumber(request.getIdOSC());
            final IndicatorDTO indicatorDTO = monthlyOSCMapper.obtainMonthlyOSC(request);
            if (indicatorDTO == null){
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
    @ApiOperation("Obtener indicadores por rango de fecha del empleado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Se obtienen los indicadores por rango el empleado", response = IndicatorResponse.class),
            @ApiResponse(code = 204, message = "El dato ingresado es incorrecto", response = IndicatorResponse.class),
            @ApiResponse(code = 400, message = "Argumentos inválidos", response = IndicatorResponse.class),
            @ApiResponse(code = 500, message = "Error inesperado del servicio web", response = IndicatorResponse.class)
    })
    public ResponseEntity<IndicatorResponse> obtainRangeIndicator (@RequestBody RangeRequest request){
        try {
            validateRequest(request);
            validateIdNumber(request.getIdEmployee());
            isDateValid(request.getInitialDate());
            isDateValid(request.getFinalDate());
            final IndicatorDTO indicatorDTO = rangeMapper.obtainRange(request);
            if (indicatorDTO == null){
                LOGGER.info("No se obtubieron indicadores para el emmpleado con ID: "+request.getIdEmployee());
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new IndicatorResponse("Empleado o indicadores no encontrados"));
            }
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

    @PostMapping(
            value = "employee/indicators/campaign",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Obtener indicadores por campania del empleado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Se obtienen los indicadores campania el empleado", response = IndicatorResponse.class),
            @ApiResponse(code = 204, message = "El dato ingresado es incorrecto", response = IndicatorResponse.class),
            @ApiResponse(code = 400, message = "Argumentos inválidos", response = IndicatorResponse.class),
            @ApiResponse(code = 500, message = "Error inesperado del servicio web", response = IndicatorResponse.class)
    })
    public ResponseEntity<IndicatorResponse> obtainCampaignIndicator (@RequestBody CampaignRequest request){
        try {
            validateRequest(request);
            validateIdNumber(request.getIdEmployee());
            validateIdNumber(request.getIdCampaign());
            final IndicatorDTO indicatorDTO = campaignMapper.obtainCampaign(request);
            if (indicatorDTO == null){
                LOGGER.info("No se obtuvo el indicador por campania: {} para el empleado con ID: {}"
                        ,request.getIdCampaign()
                        ,request.getIdEmployee());
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new IndicatorResponse("Indicadores no encontrados"));
            }
            final IndicatorResponse response = indicatorBuilder.apply(indicatorDTO);
            LOGGER.info("Se obtuvo el indicador por campania: {} para el empleado con ID: {},"
                    ,request.getIdCampaign()
                    ,request.getIdEmployee());
            return ResponseEntity.ok(response);
        }catch (IllegalArgumentException iae){
            LOGGER.warn("Los parametros ingresados son invalidos", iae);
            return ResponseEntity.badRequest().body(new IndicatorResponse(iae.getMessage()));
        }catch (Exception ex) {
            LOGGER.error("Ocurrio un error al intentar obtener los indicadores de la campania {} para el empleado id "
                    ,request.getIdCampaign(),request.getIdEmployee(), ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR).body(new IndicatorResponse(ex.getMessage()));
        }
    }

}
