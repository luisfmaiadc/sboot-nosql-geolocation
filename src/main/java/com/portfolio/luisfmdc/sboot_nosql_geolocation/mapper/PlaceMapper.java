package com.portfolio.luisfmdc.sboot_nosql_geolocation.mapper;

import com.portfolio.luisfmdc.model.PlaceResponse;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.domain.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlaceMapper {

    @Mapping(source = "name", target = "nome")
    @Mapping(source = "addressComplement", target = "complemento")
    @Mapping(source = "street", target = "rua")
    @Mapping(source = "number", target = "numero")
    @Mapping(source = "neighborhood", target = "bairro")
    @Mapping(source = "city", target = "cidade")
    @Mapping(source = "state", target = "estado")
    @Mapping(source = "rating", target = "avaliacao")
    @Mapping(target = "geolocalizacao.latitude", expression = "java(place.getLocation() != null ? place.getLocation().getY() : null)")
    @Mapping(target = "geolocalizacao.longitude", expression = "java(place.getLocation() != null ? place.getLocation().getX() : null)")
    PlaceResponse toPlaceResponse(Place place);
}
