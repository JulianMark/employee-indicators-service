package com.project.indicators.service;

import com.project.indicators.builder.IndicatorBuilder;
import com.project.indicators.mapper.RangeMapper;
import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.IndicatorResponse;
import com.project.indicators.service.http.RangeRequest;
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

@DisplayName("Employee Range Indicators Service Test")
class RangeIndicatorsServiceTest {

    @Mock
    private RangeMapper rangeMapper;

    @Mock
    private IndicatorBuilder indicatorBuilder;

    @InjectMocks
    private IndicatorsService sut;

    private final RangeRequest VALID_REQUEST = new RangeRequest(1,"2019/12/01","2019/12/31");
    private final RangeRequest INVALID_REQUEST = new RangeRequest(0,"12/01/2019","31/12/2019");
    private final IndicatorDTO VALID_DTO = new IndicatorDTO(3f,800f,1f,6f,1f,null);


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Nested
    @DisplayName("Should return 400 (Bad Request)")
    class ObtainRangeIndicatorsRequestTest {

        @Test
        @DisplayName("When Request is null")
        public void obtainRangeIndicator_RequestIsNull_ReturnBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainRangeIndicator(null);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }

        @Test
        @DisplayName("When idEmployee is zero or less zero")
        public void obtainRangeIndicator_IdEmployeeIsZeroOrLessZero_ReturnBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainRangeIndicator(INVALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }

    }

    @Nested
    @DisplayName("Should return 500 (Internal Server Error)")
    class ObtainRangeIndicatorInternalServerErrorTest {

        @Test
        @DisplayName("When RangeMapper throws Exception")
        public void obtainRangeIndicator_RangeMapperThrowException_ReturnsInternalServerError(){
            when(rangeMapper.obtainRange(any())).thenThrow(new RuntimeException ("something bad happened"));
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainRangeIndicator(VALID_REQUEST);
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
        public void obtainRangeIndicator_DTOIsNull_ReturnsNonContent(){
            when(rangeMapper.obtainRange(any())).thenReturn(null);
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainRangeIndicator(VALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.NO_CONTENT));
        }

    }

    @Nested
    @DisplayName("Should return 200 (OK)")
    class ObtainRangeIndicatorStatusOKTest {

        @Test
        @DisplayName("When No Exception is Caught")
        public void obtainRange_NoExceptionCaught_ReturnsOk(){
            when(rangeMapper.obtainRange(any())).thenReturn(VALID_DTO);
            when(indicatorBuilder.apply(VALID_DTO)).thenReturn(new IndicatorResponse());
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainRangeIndicator(VALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.OK));
        }
    }

}