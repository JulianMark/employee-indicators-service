package com.project.indicators.service.http;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyRequest {

    private Integer idEmployee;
    private Integer monthNumber;
    private Integer yearNumber;
    private Integer idOSC;
}
