package com.maryanto.dimas.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class RequestBuilder {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HttpRoutingRequest {

        private String location;
        @NotNull
        @NotEmpty
        private String path;
        @NotNull
        @NotEmpty
        private HttpMethod method;
        private Map<String, Object> body;
        private Map<String, String> params;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HttpRoutingSecured {

        private String location;
        @NotNull
        @NotEmpty
        private String path;
        @NotNull
        @NotEmpty
        private HttpMethod method;
        private String tokenBearer;
        private Map<String, Object> body;
        private Map<String, String> params;
    }
}
