package com.project.indicators.mapper;

import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.HistoricalRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface HistoricalMapper {

    IndicatorDTO obtainHistorical (@Param("historical") HistoricalRequest historicalRequest);
}
