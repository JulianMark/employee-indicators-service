package com.project.indicators.service;


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
    @DisplayName("Should return 400 (Bad Request)")
    class ObtainMonthlyOSCIndicatorsRequestTest {

        @Test
        @DisplayName("When Request is null")
        public void obtainMonthlyOSCIndicator_RequestIsNull_ReturnBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyOSCIndicator(null);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }

        @Test
        @DisplayName("When idEmployee is zero or less zero")
        public void obtainMonthlyOSCIndicator_IdEmployeeIsZeroOrLessZero_ReturnBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyOSCIndicator(INVALID_REQUEST_EMPLOYEE);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }

        @Test
        @DisplayName("When idOSC is zero or less zero")
        public void obtainHistoricalIndicatorOSC_IdOSCIsZeroOrLessZero_ReturnBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyOSCIndicator(INVALID_REQUEST_OSC);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }

    }

    @Nested
    @DisplayName("Should return 500 (Internal Server Error)")
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
    @DisplayName("Should return 204 (No Content)")
    class ObtainMonthlyOSCIndicatorNoContentTest {

        @Test
        @DisplayName("When DTO is null")
        public void obtainMonthlyOSCIndicator_DTOIsNull_ReturnsNonContent(){
            when(monthlyOSCMapper.obtainMonthlyOSC(any())).thenReturn(null);
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyOSCIndicator(VALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.NO_CONTENT));
        }

    }

    @Nested
    @DisplayName("Should return 200 (OK)")
    class ObtainMonthlyOSCIndicatorStatusOKTest {

        @Test
        @DisplayName("When No Exception is Catched")
        public void obtainMonthlyOSCIndicator_NoExceptionCatched_ReturnsOk(){
            when(monthlyOSCMapper.obtainMonthlyOSC(any())).thenReturn(VALID_DTO);
            when(indicatorBuilder.apply(VALID_DTO)).thenReturn(new IndicatorResponse());
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyOSCIndicator(VALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.OK));
        }
    }

}