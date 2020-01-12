package com.project.indicators.service;

import com.project.indicators.builder.IndicatorBuilder;
import com.project.indicators.mapper.MonthlyMapper;
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

@DisplayName("Employee Monthly Indicators Service")
class MonthlyIndicatorsServiceTest {

    @Mock
    private MonthlyMapper monthlyMapper;

    @Mock
    private IndicatorBuilder indicatorBuilder;

    @InjectMocks
    private IndicatorsService sut;

    private final MonthlyRequest INVALID_REQUEST = new MonthlyRequest(null,null,null,null);
    private final MonthlyRequest VALID_REQUEST = new MonthlyRequest(1,12,2019,1);
    private final IndicatorDTO VALID_DTO = new IndicatorDTO(3f,800f,1f,6f,1f,null);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Nested
    @DisplayName("Should return 400 (Bad Request)")
    class ObtainMonthlyIndicatorsRequestTest {

        @Test
        @DisplayName("When Request is null")
        public void obtainMonthlylIndicator_RequestIsNull_ReturnBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyIndicator(null);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }

        @Test
        @DisplayName("When idEmployee is zero or less zero")
        public void obtainMonthlyIndicator_IdEmployeeIsZeroOrLessZero_ReturnBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyIndicator(INVALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }
    }

    @Nested
    @DisplayName("Should return 500 (Internal Server Error)")
    class ObtainMonthlyInternalServerErrorTest {

        @Test
        @DisplayName("When MonthlyMapper throws Exception")
        public void obtainMonthlyIndicator_MonthlyMapperThrowException_ReturnsInternalServerError(){
            when(monthlyMapper.obtainMonthly(any())).thenThrow(new RuntimeException ("something bad happened"));
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyIndicator(VALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @Nested
    @DisplayName("Should return 204 (No Content)")
    class ObtainMonthlyIndicatorNoContentTest {

        @Test
        @DisplayName("When DTO is null")
        public void obtainMonthlyIndicator_DTOIsNull_ReturnsNonContent(){
            when(monthlyMapper.obtainMonthly(any())).thenReturn(null);
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyIndicator(VALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.NO_CONTENT));
        }
    }

    @Nested
    @DisplayName("Should return 200 (OK)")
    class ObtainMonthlyIndicatorStatusOKTest {

        @Test
        @DisplayName("When No Exception is Caught")
        public void obtainMonthlyIndicator_NoExceptionCaught_ReturnsOk(){
            when(monthlyMapper.obtainMonthly(any())).thenReturn(VALID_DTO);
            when(indicatorBuilder.apply(VALID_DTO)).thenReturn(new IndicatorResponse());
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainMonthlyIndicator(VALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.OK));
        }
    }
}