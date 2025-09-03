package com.portfolio.luisfmdc.sboot_nosql_geolocation.service;


import com.portfolio.luisfmdc.model.GeolocationResponse;

public interface GeolocationService {

    GeolocationResponse searchPlace(String cep);
}
