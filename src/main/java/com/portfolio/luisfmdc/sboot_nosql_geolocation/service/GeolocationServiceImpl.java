package com.portfolio.luisfmdc.sboot_nosql_geolocation.service;

import com.portfolio.luisfmdc.model.BrasilApiResponse;
import com.portfolio.luisfmdc.model.GeolocationResponse;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.config.RouterProperties;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.mapper.GeolocationMapper;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.repository.GeolocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class GeolocationServiceImpl implements GeolocationService {

    private final GeolocationRepository repository;
    private final RouterProperties properties;
    private final RestTemplate restTemplate;
    private final GeolocationMapper mapper;

    @Override
    public GeolocationResponse searchPlace(String cep) {
        URI uri = UriComponentsBuilder.fromUriString(properties.getBrasilapi().getUrl()).pathSegment(cep).build().toUri();
        ResponseEntity<BrasilApiResponse> response = restTemplate.getForEntity(uri, BrasilApiResponse.class);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) return null;
        return mapper.toGeolocationResponse(response.getBody());
    }
}
