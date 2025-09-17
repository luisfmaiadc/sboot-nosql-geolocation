package com.portfolio.luisfmdc.sboot_nosql_geolocation.service;

import com.portfolio.luisfmdc.model.NewPlaceRequest;
import com.portfolio.luisfmdc.model.PlaceResponse;
import com.portfolio.luisfmdc.model.SearchPlaceResponse;

import java.util.List;

public interface PlaceService {

    SearchPlaceResponse searchPlaceByCep(String cep);
    PlaceResponse insertNewPlace(NewPlaceRequest request);
    List<PlaceResponse> searchPlaceByQuery(String name, Double latitude, Double longitude, Integer raio);
}
