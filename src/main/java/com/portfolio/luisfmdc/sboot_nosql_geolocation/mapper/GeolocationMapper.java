package com.portfolio.luisfmdc.sboot_nosql_geolocation.mapper;

import com.portfolio.luisfmdc.model.BrasilApiResponse;
import com.portfolio.luisfmdc.model.GeolocationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GeolocationMapper {

    @Mapping(source = "cep", target = "cep")
    @Mapping(source = "state", target = "estado")
    @Mapping(source = "city", target = "cidade")
    @Mapping(source = "neighborhood", target = "bairro")
    @Mapping(source = "street", target = "logradouro")
    @Mapping(source = "location.coordinates.latitude", target = "geolocalizacao.latitude")
    @Mapping(source = "location.coordinates.longitude", target = "geolocalizacao.longitude")
    GeolocationResponse toGeolocationResponse(BrasilApiResponse brasilApiResponse);
}
