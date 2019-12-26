package com.project.indicators.service.http;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RangeRequest {

    private Integer idEmployee;
    private String initialDate;
    private String finalDate;
}
