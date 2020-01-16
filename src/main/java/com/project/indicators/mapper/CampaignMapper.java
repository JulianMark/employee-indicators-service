package com.project.indicators.mapper;

import com.project.indicators.model.dto.IndicatorDTO;
import com.project.indicators.service.http.CampaignRequest;
import com.project.indicators.service.http.OSCRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CampaignMapper {

    IndicatorDTO obtainCampaign (@Param("campaignRequest") CampaignRequest campaignRequest);
}
