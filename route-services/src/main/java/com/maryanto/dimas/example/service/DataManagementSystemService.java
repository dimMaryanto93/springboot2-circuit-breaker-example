package com.maryanto.dimas.example.service;

import com.maryanto.dimas.example.dto.RequestBuilder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
@Slf4j
public class DataManagementSystemService {

    @Autowired
    private RestTemplate template;

    @HystrixCommand(fallbackMethod = "fallbackResponse")
    public ResponseEntity<?> callDataManagementSystem(RequestBuilder.HttpRoutingSecured request) {
        HttpHeaders headers = new HttpHeaders();

        if (StringUtils.isNotEmpty(request.getTokenBearer()))
            headers.setBearerAuth(request.getTokenBearer());

        HttpEntity entity;
        if (request.getBody() != null)
            entity = new HttpEntity(request.getBody(), headers);
        else
            entity = new HttpEntity(headers);

        String host = "http://localhost:4041";

        UriComponentsBuilder uriComponent = UriComponentsBuilder.fromHttpUrl(String.format("%s%s", host, request.getPath()));

        if (request.getParams() != null && !request.getParams().isEmpty()) {
            for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                uriComponent.queryParam(entry.getKey(), entry.getValue());
            }
        }

        log.info("uri: {}", uriComponent.toUriString());
        return this.template.exchange(uriComponent.toUriString(), request.getMethod(), entity, String.class);
    }

    public ResponseEntity<?> fallbackResponse(RequestBuilder.HttpRoutingSecured request) {
        return ResponseEntity.notFound().build();
    }
}
