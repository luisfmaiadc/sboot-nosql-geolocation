package com.portfolio.luisfmdc.sboot_nosql_geolocation.domain;

import com.portfolio.luisfmdc.model.NewPlaceRequest;
import com.portfolio.luisfmdc.model.SearchPlaceResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "pois")
public class Place {

    @Id
    private String id;
    private String name;
    private String addressComplement;
    private String cep;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;
    private Double rating;

    public Place(SearchPlaceResponse searchPlaceResponse, NewPlaceRequest request) {
        this.name = request.getNome();
        this.addressComplement = request.getComplemento() != null && request.getComplemento().isPresent() ? request.getComplemento().get() : null;
        this.cep = searchPlaceResponse.getCep();
        this.street = searchPlaceResponse.getRua();
        this.number = request.getNumero();
        this.neighborhood = searchPlaceResponse.getBairro();
        this.city = searchPlaceResponse.getCidade();
        this.state = searchPlaceResponse.getEstado();
        this.location = new GeoJsonPoint(searchPlaceResponse.getGeolocalizacao().getLongitude(), searchPlaceResponse.getGeolocalizacao().getLatitude());
        this.rating = request.getAvaliacao();
    }
}
