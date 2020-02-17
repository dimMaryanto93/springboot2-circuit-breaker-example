package com.maryanto.dimas.example.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.maryanto.dimas.example.dto.RequestBuilder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.ClientHttpResponseStatusCodeException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
@Slf4j
public class DataManagementSystemService {

    @Autowired
    private RestTemplate template;

    @HystrixCommand(
            fallbackMethod = "fallbackResponse",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
            },
            ignoreExceptions = {
                    HttpStatusCodeException.class,
                    JsonMappingException.class,
                    ClientHttpResponseStatusCodeException.class
            }
    )
    public ResponseEntity<?> callDataManagementSystem(RequestBuilder.HttpRoutingSecured request) {
        HttpHeaders headers = new HttpHeaders();

        if (StringUtils.isNotEmpty(request.getTokenBearer()))
            headers.setBearerAuth(request.getTokenBearer());

        HttpEntity entity = null;
        if (request.getBody() != null)
            entity = new HttpEntity(request.getBody(), headers);
        else
            entity = new HttpEntity(headers);

        String host = "http://localhost:4040";

        UriComponentsBuilder uriComponent = UriComponentsBuilder.fromHttpUrl(String.format("%s%s", host, request.getPath()));

        if (request.getParams() != null && !request.getParams().isEmpty()) {
            for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                uriComponent.queryParam(entry.getKey(), entry.getValue());
            }
        }

        log.info("uri: {}", uriComponent.toUriString());
        ResponseEntity<String> exchange = null;
        try {
            exchange = this.template.exchange(uriComponent.toUriString(), request.getMethod(), entity, String.class);
        } catch (HttpClientErrorException hcee) {
            HttpStatus statusCode = hcee.getStatusCode();
            switch (statusCode) {
                case NOT_FOUND:
                    exchange = ResponseEntity.notFound().build();
                    break;
                case FORBIDDEN:
                    exchange = new ResponseEntity<>(HttpStatus.FORBIDDEN);
                    break;
                case BAD_REQUEST:
                    exchange = new ResponseEntity<>(hcee.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
                    break;
                case UNAUTHORIZED:
                    exchange = new ResponseEntity(hcee.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
                    break;
                default:
                    new HystrixBadRequestException("Exception thrown hystrix call", hcee);
            }
        }
        return exchange;
    }

    public ResponseEntity<?> fallbackResponse(RequestBuilder.HttpRoutingSecured request) {
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
}
