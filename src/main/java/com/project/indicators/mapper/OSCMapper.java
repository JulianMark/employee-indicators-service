package com.project.indicators.mapper;

import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.HistoricalRequest;
import com.project.indicators.service.http.OSCRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface OSCMapper {

    IndicatorDTO obtainOSC(@Param("oscRequest") OSCRequest oscRequest);
}
