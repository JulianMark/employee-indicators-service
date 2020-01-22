package com.project.indicators.service;


import com.project.indicators.Utils.IndicatorValidator;
import com.project.indicators.builder.IndicatorBuilder;
import com.project.indicators.mapper.MonthlyOSCMapper;
import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.IndicatorResponse;
import com.project.indicators.service.http.MonthlyRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Employee Monthly for OSC Indicators Service")
class MonthlyOSCIndicatorsServiceTest {

    @Mock
    private MonthlyOSCMapper monthlyOSCMapper;

    @Mock
    private IndicatorBuilder indicatorBuilder;

    @Mock
    private IndicatorValidator indicatorValidator;

    @InjectMocks
    private IndicatorsService sut;

    private final MonthlyRequest INVALID_REQUEST_EMPLOYEE = new MonthlyRequest(null,12,2019,1);
    private final MonthlyRequest INVALID_REQUEST_OSC = new MonthlyRequest(1,12,2019,null);
    private final MonthlyRequest VALID_REQUEST = new MonthlyRequest(1,1,2020,1);
    private final IndicatorDTO VALID_DTO = new IndicatorDTO(3f,800f,1f,6f,1f,null);private final IndicatorDTO INVALID_DTO = new IndicatorDTO(null,800f,1f,6f,1f,null);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Nested
    @DisplayName("Should returns 400 (Bad Request)")
    class ObtainMonthlyOSCIndicatorsRequestTest {

        @Test
        @DisplayName("When Request is null")
        public void obtainMonthlyOSCIndicator_RequestIsNull_ReturnsBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyOSCIndicator(null);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }

        @Test
        @DisplayName("When idEmployee is zero or less zero")
        public void obtainMonthlyOSCIndicator_IdEmployeeIsZeroOrLessZero_ReturnsBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyOSCIndicator(INVALID_REQUEST_EMPLOYEE);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }

        @Test
        @DisplayName("When idOSC is zero or less zero")
        public void obtainHistoricalIndicatorOSC_IdOSCIsZeroOrLessZero_ReturnsBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyOSCIndicator(INVALID_REQUEST_OSC);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }
    }

    @Nested
    @DisplayName("Should returns 500 (Internal Server Error)")
    class ObtainMonthlyOSCIndicatorInternalServerErrorTest {

        @Test
        @DisplayName("When MonthlyOSCMapper throws Exception")
        public void obtainMonthlyOSCIndicator_MonthlyOSCMapperThrowException_ReturnsInternalServerError(){
            when(monthlyOSCMapper.obtainMonthlyOSC(any())).thenThrow(new RuntimeException ("something bad happened"));
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyOSCIndicator(VALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @Nested
    @DisplayName("Should returns 204 (No Content)")
    class ObtainMonthlyOSCIndicatorNoContentTest {

        @Test
        @DisplayName("When DTO is null")
        public void obtainMonthlyOSCIndicator_DTOIsNull_ReturnsNonContent(){
            when(monthlyOSCMapper.obtainMonthlyOSC(any())).thenReturn(new IndicatorDTO());
            when(indicatorValidator.obtainIndicatorValidator())
                    .thenReturn(indicatorDTO -> ResponseEntity.noContent().build());
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyOSCIndicator(VALID_REQUEST);
            assertThat("Status Code Response", responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
        }

    }

    @Nested
    @DisplayName("Should returns 200 (OK)")
    class ObtainMonthlyOSCIndicatorStatusOKTest {

        @Test
        @DisplayName("When No Exception is Caught")
        public void obtainMonthlyOSCIndicator_NoExceptionCaught_ReturnsOk(){
            final IndicatorResponse response = indicatorBuilder.apply(VALID_DTO);
            when(monthlyOSCMapper.obtainMonthlyOSC(any())).thenReturn(VALID_DTO);
            when(indicatorValidator.obtainIndicatorValidator())
                    .thenReturn(indicatorDTO -> ResponseEntity.ok(response));
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyOSCIndicator(VALID_REQUEST);
            assertThat("Status Code Response", responseEntity.getStatusCode(), is(HttpStatus.OK));
            assertThat(responseEntity.getBody(), is(response));
        }
    }
}