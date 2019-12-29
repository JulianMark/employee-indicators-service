package com.project.indicators.service.http;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OSCRequest {

    @ApiModelProperty(notes = "Necesario el id del empleado", required = true, example = "1")
    private Integer idEmployee;
    @ApiModelProperty (notes = "Necesario el numero de id de la OSC que se desea consultar", required = true, example = "1")
    private Integer idOSC;
}
