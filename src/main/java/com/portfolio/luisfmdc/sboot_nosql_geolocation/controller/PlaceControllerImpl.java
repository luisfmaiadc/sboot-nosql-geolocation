package com.portfolio.luisfmdc.sboot_nosql_geolocation.controller;

import com.portfolio.luisfmdc.api.V1Api;
import com.portfolio.luisfmdc.model.NewPlaceRequest;
import com.portfolio.luisfmdc.model.PlaceResponse;
import com.portfolio.luisfmdc.model.SearchPlaceResponse;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaceControllerImpl implements V1Api {

    private final PlaceService service;

    @Override
    public ResponseEntity<SearchPlaceResponse> searchPlaceByCep(String cep) {
        return new ResponseEntity<>(service.searchPlaceByCep(cep), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PlaceResponse> insertNewPlace(NewPlaceRequest newPlaceRequest) {
        return new ResponseEntity<>(service.insertNewPlace(newPlaceRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<PlaceResponse>> searchPlaceByQuery(String nome, Double latitude, Double longitude, Integer raio) {
        return new ResponseEntity<>(service.searchPlaceByQuery(nome, latitude, longitude, raio), HttpStatus.OK);
    }
}
