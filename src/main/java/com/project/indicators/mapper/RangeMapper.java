package com.project.indicators.mapper;

import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.MonthlyRequest;
import com.project.indicators.service.http.RangeRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface RangeMapper {

    IndicatorDTO obtainRange(@Param("range") RangeRequest request);
}
