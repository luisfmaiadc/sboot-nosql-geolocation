package com.portfolio.luisfmdc.sboot_nosql_geolocation.controller;

import com.portfolio.luisfmdc.api.LocationApi;
import com.portfolio.luisfmdc.model.GeolocationResponse;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.service.GeolocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GeolocationControllerImpl implements LocationApi {

    private final GeolocationService service;

    @Override
    public ResponseEntity<GeolocationResponse> searchPlace(String cep) {
        return new ResponseEntity<>(service.searchPlace(cep), HttpStatus.OK);
    }
}
