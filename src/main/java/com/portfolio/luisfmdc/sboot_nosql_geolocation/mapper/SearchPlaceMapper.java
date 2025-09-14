package com.portfolio.luisfmdc.sboot_nosql_geolocation.mapper;

import com.portfolio.luisfmdc.model.SearchPlaceResponse;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.dto.BrasilApiResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SearchPlaceMapper {

    @Mapping(source = "street", target = "rua")
    @Mapping(source = "neighborhood", target = "bairro")
    @Mapping(source = "city", target = "cidade")
    @Mapping(source = "state", target = "estado")
    @Mapping(source = "location.coordinates.latitude", target = "geolocalizacao.latitude")
    @Mapping(source = "location.coordinates.longitude", target = "geolocalizacao.longitude")
    SearchPlaceResponse toSearchPlaceResponse(BrasilApiResponse apiResponse);
}
