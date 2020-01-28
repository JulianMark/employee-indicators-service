package com.project.indicators.service.http;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RangeRequest {

    @ApiModelProperty(notes = "Necesario el id del empleado", required = true, example = "1")
    private Integer idEmployee;
    @ApiModelProperty (notes = "Necesario una fecha de inicio", required = true, example = "2019/12/01")
    private String initialDate;
    @ApiModelProperty (notes = "Necesario una fecha de cierre", required = true, example = "2019/12/31")
    private String finalDate;
}
