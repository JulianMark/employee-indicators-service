package com.project.indicators.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static com.project.indicators.Utils.Utils.*;

@DisplayName("Utils Test")
class UtilsTest {

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("When request is null return Illegal Argument Exception")
    void validateRequest_RequestIsNull_ReturnsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> validateRequest(null));
    }

    @Test
    @DisplayName("When id is null or lees zero return Illegal Argument Exception")
    void validateIdNumber_IdIsNullOrLessZero_ReturnsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> validateIdNumber(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> validateIdNumber(0));
    }

    @Test
    @DisplayName("When DTO is null return Illegal Argument Exception")
    void validateIndicatorDTO_DTOIsNull_ReturnsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> validateIndicatorDTO(null));
    }

    @Test
    @DisplayName("When month is null or out range return Illegal Argument Exception")
    void validateMonth_MonthIsNullOrOutRange_ReturnsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> validateMonth(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> validateMonth(0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> validateMonth(13));
    }

    @Test
    @DisplayName("When year is null or less 2015 return Illegal Argument Exception")
    void validateYear_YearIsNullOrLees2015_ReturnsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> validateYear(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> validateYear(2014));
        Assertions.assertThrows(IllegalArgumentException.class, () -> validateYear(2021));
    }

    @Test
    @DisplayName("When date is incorrect format is null return Illegal Argument Exception")
    void isDateValid_DateIsIncorrectFormat_ReturnsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> isDateValid("11/05/2019"));
    }
}