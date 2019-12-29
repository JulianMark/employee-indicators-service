package com.project.indicators.service.http;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IndicatorRequest {

    @ApiModelProperty (notes = "Necesario el id del empleado", required = true, example = "1")
    private Integer idEmployee;
}
