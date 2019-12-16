package com.project.indicators.service.http;

import com.project.indicators.model.OSC;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OSCsResponse {

    private List<OSC> oscs;
    private String errorMessage;

    public OSCsResponse(List<OSC> oscs) {
        this.oscs = oscs;
    }
}
