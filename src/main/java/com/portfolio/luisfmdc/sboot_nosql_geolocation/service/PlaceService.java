package com.portfolio.luisfmdc.sboot_nosql_geolocation.service;

import com.portfolio.luisfmdc.model.NewPlaceRequest;
import com.portfolio.luisfmdc.model.PlaceResponse;
import com.portfolio.luisfmdc.model.SearchPlaceResponse;

public interface PlaceService {

    SearchPlaceResponse searchPlace(String cep);
    PlaceResponse insertNewPlace(NewPlaceRequest request);
}
