package com.project.indicators.mapper;

import com.project.indicators.model.OSC;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface OSCMapper {

    List<OSC> obtainOSCs(@Param("idEmployee") Integer idEmployee);
}
