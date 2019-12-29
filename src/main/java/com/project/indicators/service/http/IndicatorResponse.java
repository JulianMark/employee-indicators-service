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
public class IndicatorResponse {

   @ApiModelProperty (notes= "Cantidad de donaciones del empleado")
   private Float totalDonations;
   @ApiModelProperty (notes= "Monto total de las donaciones del empleado")
   private Float totalAmountDonations;
   @ApiModelProperty (notes= "Cantidad de donaciones con tarjeta de credito del empleado")
   private Float creditType;
   @ApiModelProperty (notes= "Cantidad de horas de captacion del empleado")
   private Float totalProductiveHours;
   @ApiModelProperty (notes= "Cantidad de horas sin captacion del empleado")
   private Float totalNonProductiveHours;
   @ApiModelProperty (notes= "Productividad por hora del empleado")
   private Float totalAverageCatchment;
   @ApiModelProperty (notes= "Monto promedio del empleado")
   private Float totalAverageAmount;
   @ApiModelProperty (notes= "Porentaje de donaciones con tarjeta de credito del empleado")
   private Float totalAverageCreditType;
   @ApiModelProperty (notes="Mensaje de error, en caso de que falle el WS")
   private String errorMessage;

    public IndicatorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
