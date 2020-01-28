package com.project.indicators.Utils;

import com.project.indicators.builder.IndicatorBuilder;
import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.IndicatorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
@DisplayName("Indicator Validator Test")
class IndicatorValidatorTest {

    private final IndicatorDTO VALID_DTO = new IndicatorDTO(3f,800f
            ,1f,6f,1f,null);

    private static ResponseEntity<IndicatorResponse> get (){
        return ResponseEntity.noContent().build();
    }

    @Mock
    private IndicatorBuilder indicatorBuilder;

    @InjectMocks
    private IndicatorValidator sut;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("When IndicatorDTO is not null returns No Content (204)")
    public void obtainEmptyIndicator_IndicatorDTOIsNull_ReturnsNoContent (){
        Supplier<ResponseEntity<IndicatorResponse>> expected = IndicatorValidatorTest::get;
        Supplier<ResponseEntity<IndicatorResponse>> actual = sut.obtainEmptyIndicator();

        assertThat(actual.get().toString() , is (expected.get().toString()));
    }

    @Test
    @DisplayName("When IndicatorDTO is not null returns ok (200)")
    public void obtainIndicator_IndicatorDTOIsNotNull_ReturnsOK (){
        IndicatorResponse expected = indicatorBuilder.apply(VALID_DTO);

        ResponseEntity<IndicatorResponse> responseEntity = sut.obtainIndicator(VALID_DTO);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(expected));
    }
}