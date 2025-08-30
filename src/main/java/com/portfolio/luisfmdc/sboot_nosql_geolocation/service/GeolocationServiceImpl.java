package com.portfolio.luisfmdc.sboot_nosql_geolocation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.luisfmdc.model.PlaceResponse;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.repository.GeolocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GeolocationServiceImpl implements GeolocationService {

    private final GeolocationRepository repository;
    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;

    @Override
    public PlaceResponse searchPlace(String cep) {
        return null;
    }
}
