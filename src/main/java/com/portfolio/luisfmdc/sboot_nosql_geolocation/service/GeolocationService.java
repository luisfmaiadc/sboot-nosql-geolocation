package com.portfolio.luisfmdc.sboot_nosql_geolocation.service;

import com.portfolio.luisfmdc.model.PlaceResponse;

public interface GeolocationService {

    PlaceResponse searchPlace(String cep);
}
