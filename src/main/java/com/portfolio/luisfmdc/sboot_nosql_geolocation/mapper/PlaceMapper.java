package com.portfolio.luisfmdc.sboot_nosql_geolocation.mapper;

import com.portfolio.luisfmdc.model.PlaceResponse;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.domain.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlaceMapper {

    @Mapping(source = "street", target = "rua")
    @Mapping(source = "number", target = "numero")
    @Mapping(source = "neighborhood", target = "bairro")
    @Mapping(source = "city", target = "cidade")
    @Mapping(source = "state", target = "estado")
    @Mapping(source = "rating", target = "avaliacao")
    @Mapping(source = "geolocation.latitude", target = "geolocalizacao.latitude")
    @Mapping(source = "geolocation.longitude", target = "geolocalizacao.longitude")
    PlaceResponse toPlaceResponse(Place place);
}
