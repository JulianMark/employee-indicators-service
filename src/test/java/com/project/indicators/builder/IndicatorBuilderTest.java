package com.project.indicators.builder;

import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.IndicatorResponse;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Indicator Builder Test")
class IndicatorBuilderTest {

    @InjectMocks
    private IndicatorBuilder sut;

    private final IndicatorDTO VALID_DTO = new IndicatorDTO(3f
                                                            ,800f
                                                            ,1f
                                                            ,6f
                                                            ,1f
                                                            ,null);


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Nested
    @DisplayName("Validate Method numericConverter")
    class ValidateNumericConverter {

        @Test
        @DisplayName("When num is null")
        public void numericIsNull_ReturnsZero () {
            Float result = sut.numericConverter(null);
            assertThat(result,is(equalTo(0f)));
        }

        @Test
        @DisplayName("When num has value")
        public void numericHasValue_ReturnsValue () {
            Float actualResult = 12f;
            Float result = sut.numericConverter(actualResult);
            assertThat(result,is(equalTo(actualResult)));
        }
    }

    @Nested
    @DisplayName("Validate indicators method")
    class ValidateIndicatorsMethod {

        @Test
        @DisplayName("When method TotalAverageCactment returns result")
        public void obtainTotalAverageCatchment_ReturnsResult () {
            Float actualResult = sut.obtainTotalAverageCatchment(VALID_DTO);
            Float expectedResult = 0.5f;
            assertThat(actualResult,is(equalTo(expectedResult)));
        }

        @Test
        @DisplayName("When method TotalAverageAmount returns result")
        public void obtainTotalAverageAmount_ReturnsResult () {
            Float actualResult = sut.obtainTotalAverageAmount(VALID_DTO);
            Float expectedResult = 266.66666f;
            assertThat(actualResult,is(equalTo(expectedResult)));
        }

        @Test
        @DisplayName("When method TotalAverageCreditType returns result")
        public void obtainTotalAverageCreditType_ReturnsResult () {
            Float actualResult = sut.obtainTotalAverageCreditType(VALID_DTO);
            Float expectedResult = 33.333336f;
            assertThat(actualResult,is(equalTo(expectedResult)));
        }
    }

    @Nested
    @DisplayName("Validate method apply")
    class ValidateMethodApply {

        @Test
        @DisplayName("When method apply returns IndicatorResponse")
        public void apply_ValidDTO_ReturnsIndicatorResponse () {
            IndicatorResponse actualResponse = sut.apply(VALID_DTO);
            IndicatorResponse expectedResponse = new IndicatorResponse(3f
                                                                        ,800f
                                                                        ,1f
                                                                        ,6f
                                                                        ,1f
                                                                        ,0.5f
                                                                        ,266.66666f
                                                                        ,33.333336f
                                                                        ,null);
            assertThat(actualResponse.getTotalDonations(),is(equalTo(expectedResponse.getTotalDonations())));
            assertThat(actualResponse.getTotalAmountDonations(),is(equalTo(expectedResponse.getTotalAmountDonations())));
            assertThat(actualResponse.getCreditType(),is(equalTo(expectedResponse.getCreditType())));
            assertThat(actualResponse.getTotalProductiveHours(),is(equalTo(expectedResponse.getTotalProductiveHours())));
            assertThat(actualResponse.getTotalNonProductiveHours(),is(equalTo(expectedResponse.getTotalNonProductiveHours())));
            assertThat(actualResponse.getTotalAverageCatchment(),is(equalTo(expectedResponse.getTotalAverageCatchment())));
            assertThat(actualResponse.getTotalAverageAmount(),is(equalTo(expectedResponse.getTotalAverageAmount())));
            assertThat(actualResponse.getTotalAverageCreditType(),is(equalTo(expectedResponse.getTotalAverageCreditType())));
            assertThat(actualResponse.getErrorMessage(),is(equalTo(expectedResponse.getErrorMessage())));

        }
    }
}
