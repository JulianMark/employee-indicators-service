package com.project.indicators.service;

import com.project.indicators.builder.IndicatorBuilder;
import com.project.indicators.mapper.OSCMapper;
import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.IndicatorResponse;
import com.project.indicators.service.http.OSCRequest;
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

@DisplayName("Employee Historical Indicators OSC Service Test")
class HistoricalIndicatorsOSCServiceTest {

    @Mock
    private OSCMapper oscMapper;

    @Mock
    private IndicatorBuilder indicatorBuilder;

    @InjectMocks
    private IndicatorsService sut;

    private final OSCRequest INVALID_REQUEST_EMPLOYEE = new OSCRequest(0,1);
    private final OSCRequest INVALID_REQUEST_OSC = new OSCRequest(1,0);
    private final OSCRequest VALID_REQUEST =  new OSCRequest(1,1);
    private final IndicatorDTO VALID_DTO = new IndicatorDTO(3f,800f,1f,6f,1f,null);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Nested
    @DisplayName("Should return 400 (Bad Request)")
    class ObtainHistoricalIndicatorOSCRequestTest {

        @Test
        @DisplayName("When Request is null")
        public void obtainHistoricalIndicatorOSC_RequestIsNull_ReturnBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainHistoricalOSC(null);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }

        @Test
        @DisplayName("When idEmployee is zero or less zero")
        public void obtainHistoricalIndicatorOSC_IdEmployeeIsZeroOrLessZero_ReturnBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainHistoricalOSC(INVALID_REQUEST_EMPLOYEE);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }

        @Test
        @DisplayName("When idOSC is zero or less zero")
        public void obtainHistoricalIndicatorOSC_IdOSCIsZeroOrLessZero_ReturnBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainHistoricalOSC(INVALID_REQUEST_OSC);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }

    }

    @Nested
    @DisplayName("Should return 500 (Internal Server Error)")
    class ObtainHistoricalIndicatorOSCInternalServerErrorTest {

        @Test
        @DisplayName("When OSCMapper throws Exception")
        public void obtainHistoricalIndicatorOSC_OSCMapperThrowException_ReturnsInternalServerError(){
            when(oscMapper.obtainOSC(any())).thenThrow(new RuntimeException ("something bad happened"));
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainHistoricalOSC(VALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @Nested
    @DisplayName("Should return 204 (No Content)")
    class ObtainHistoricalIndicatorOSCNoContentTest {

        @Test
        @DisplayName("When DTO is null")
        public void obtainHistoricalIndicatorOSC_DTOIsNull_ReturnsNonContent(){
            when(oscMapper.obtainOSC(any())).thenReturn(null);
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainHistoricalOSC(VALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.NO_CONTENT));
        }
    }

    @Nested
    @DisplayName("Should return 200 (OK)")
    class ObtainHistoricalIndicatorOSCStatusOKTest {

        @Test
        @DisplayName("When No Exception is Catched")
        public void obtainHistoricalIndicatorOSC_NoExceptionCatched_ReturnsOk(){
            when(oscMapper.obtainOSC(any())).thenReturn(VALID_DTO);
            when(indicatorBuilder.apply(VALID_DTO)).thenReturn(new IndicatorResponse());
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainHistoricalOSC(VALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.OK));
        }
    }

}