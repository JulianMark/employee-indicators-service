package com.project.indicators.service.http;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyRequest {

    @ApiModelProperty (notes = "Necesario el id del empleado", required = true, example = "1")
    private Integer idEmployee;
    @ApiModelProperty (notes = "Necesario el numero de mes que se desea consultar", required = true, example = "10")
    private Integer monthNumber;
    @ApiModelProperty (notes = "Necesario el numero de año que se desea consultar",required = true, example = "2019")
    private Integer yearNumber;
    @ApiModelProperty (notes = "Necesario el numero de id de la OSC que se desea consultar", required = true, example = "1")
    private Integer idOSC;
}
