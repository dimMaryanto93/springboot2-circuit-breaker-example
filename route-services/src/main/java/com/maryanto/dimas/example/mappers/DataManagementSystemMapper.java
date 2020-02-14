package com.maryanto.dimas.example.mappers;

import com.maryanto.dimas.example.dto.RequestBuilder;
import com.maryanto.dimas.plugins.web.commons.mappers.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

public class DataManagementSystemMapper {

    @Mapper
    public interface RequestMapper extends ObjectMapper<RequestBuilder.HttpRoutingRequest, RequestBuilder.HttpRoutingSecured> {
        RequestMapper converter = Mappers.getMapper(RequestMapper.class);
    }
}
