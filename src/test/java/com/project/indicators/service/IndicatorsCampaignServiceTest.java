package com.project.indicators.service;

import com.project.indicators.builder.IndicatorBuilder;
import com.project.indicators.mapper.CampaignMapper;
import com.project.indicators.mapper.OSCMapper;
import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.CampaignRequest;
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

@DisplayName("Employee Campaign Indicators Service Test")
class IndicatorsCampaignServiceTest {

    @Mock
    private CampaignMapper campaignMapper;

    @Mock
    private IndicatorBuilder indicatorBuilder;

    @InjectMocks
    private IndicatorsService sut;

    private final CampaignRequest INVALID_REQUEST_EMPLOYEE = new CampaignRequest(0,1);
    private final CampaignRequest INVALID_REQUEST_CAMPAIGN = new CampaignRequest(1,0);
    private final CampaignRequest VALID_REQUEST =  new CampaignRequest(1,1);
    private final IndicatorDTO VALID_DTO = new IndicatorDTO(3f,800f,1f,6f,1f,null);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Nested
    @DisplayName("Should return 400 (Bad Request)")
    class ObtainCampaignIndicatorOSCRequestTest {

        @Test
        @DisplayName("When Request is null")
        public void obtainCampaignIndicator_RequestIsNull_ReturnBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainCampaignIndicator(null);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }

        @Test
        @DisplayName("When idEmployee is zero or less zero")
        public void obtainCampaignIndicator_IdEmployeeIsZeroOrLessZero_ReturnBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainCampaignIndicator(INVALID_REQUEST_EMPLOYEE);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }

        @Test
        @DisplayName("When idOSC is zero or less zero")
        public void obtainHistoricalIndicatorOSC_IdOSCIsZeroOrLessZero_ReturnBadRequest(){
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainCampaignIndicator(INVALID_REQUEST_CAMPAIGN);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.BAD_REQUEST));
        }

    }

    @Nested
    @DisplayName("Should return 500 (Internal Server Error)")
    class ObtainCampaignIndicatorInternalServerErrorTest {

        @Test
        @DisplayName("When CampaignMapper throws Exception")
        public void obtainCampaignIndicator_CampaignMapperThrowException_ReturnsInternalServerError(){
            when(campaignMapper.obtainCampaign(any())).thenThrow(new RuntimeException("something bad happened"));
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainCampaignIndicator(VALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @Nested
    @DisplayName("Should return 204 (No Content)")
    class ObtainCampaignIndicatorNoContentTest {

        @Test
        @DisplayName("When DTO is null")
        public void obtainCampaignIndicator_DTOIsNull_ReturnsNonContent(){
            when(campaignMapper.obtainCampaign(any())).thenReturn(null);
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainCampaignIndicator(VALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.NO_CONTENT));
        }
    }

    @Nested
    @DisplayName("Should return 200 (OK)")
    class ObtainCampaignIndicatorStatusOKTest {

        @Test
        @DisplayName("When No Exception is Caught")
        public void obtainCampaignIndicator_NoExceptionCaught_ReturnsOk(){
            when(campaignMapper.obtainCampaign(any())).thenReturn(VALID_DTO);
            when(indicatorBuilder.apply(VALID_DTO)).thenReturn(new IndicatorResponse());
            ResponseEntity<IndicatorResponse> responseEntity = sut.obtainCampaignIndicator(VALID_REQUEST);
            assertThat("Status Code Response",
                    responseEntity.getStatusCode(),
                    is(HttpStatus.OK));
        }
    }

}