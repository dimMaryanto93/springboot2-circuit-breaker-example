package com.maryanto.dimas.example.controller;

import com.maryanto.dimas.example.dto.RequestBuilder;
import com.maryanto.dimas.example.dto.RequestBuilder.HttpRoutingSecured;
import com.maryanto.dimas.example.mappers.DataManagementSystemMapper;
import com.maryanto.dimas.example.service.DataManagementSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/dms")
public class DataManagementSystemController {

    @Autowired
    private DataManagementSystemService service;

    @PostMapping("/request")
    public ResponseEntity<?> call(@RequestBody RequestBuilder.HttpRoutingRequest body) {
        HttpRoutingSecured request = DataManagementSystemMapper.RequestMapper.converter.convertToDto(body);
        return service.callDataManagementSystem(request);
    }
}
