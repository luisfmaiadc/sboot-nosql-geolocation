package com.portfolio.luisfmdc.sboot_nosql_geolocation.controller;

import com.portfolio.luisfmdc.api.LocationApi;
import com.portfolio.luisfmdc.model.NewPlaceRequest;
import com.portfolio.luisfmdc.model.PlaceResponse;
import com.portfolio.luisfmdc.model.SearchPlaceResponse;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlaceControllerImpl implements LocationApi {

    private final PlaceService service;

    @Override
    public ResponseEntity<SearchPlaceResponse> searchPlace(String cep) {
        return new ResponseEntity<>(service.searchPlace(cep), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PlaceResponse> insertNewPlace(NewPlaceRequest newPlaceRequest) {
        return new ResponseEntity<>(service.insertNewPlace(newPlaceRequest), HttpStatus.CREATED);
    }
}
