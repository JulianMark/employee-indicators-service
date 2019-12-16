package com.project.indicators.service;

import com.project.indicators.mapper.OSCMapper;
import com.project.indicators.model.OSC;
import com.project.indicators.service.http.OSCsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndicatorsService {

    private final OSCMapper oscMapper;

    @Autowired
    public IndicatorsService(OSCMapper oscMapper) {
        this.oscMapper = oscMapper;
    }

    @GetMapping(
        value = "employee/indicators/{idEmployee}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OSCsResponse> obtainOSCs(@PathVariable Integer idEmployee) {
        final List<OSC> oscs = oscMapper.obtainOSCs(idEmployee);
        return ResponseEntity.ok( new OSCsResponse(oscs));
    }
}
