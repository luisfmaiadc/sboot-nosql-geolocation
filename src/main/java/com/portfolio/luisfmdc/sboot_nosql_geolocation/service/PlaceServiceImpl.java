package com.portfolio.luisfmdc.sboot_nosql_geolocation.service;

import com.portfolio.luisfmdc.model.NewPlaceRequest;
import com.portfolio.luisfmdc.model.PlaceResponse;
import com.portfolio.luisfmdc.model.SearchPlaceResponse;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.config.RouterProperties;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.config.exception.ResourceNotFoundException;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.domain.Place;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.dto.BrasilApiResponse;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.mapper.PlaceMapper;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.mapper.SearchPlaceMapper;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository repository;
    private final RouterProperties properties;
    private final RestTemplate restTemplate;
    private final PlaceMapper placeMapper;
    private final SearchPlaceMapper searchPlaceMapper;

    @Override
    public SearchPlaceResponse searchPlace(String cep) {
        URI uri = UriComponentsBuilder.fromUriString(properties.getBrasilapi().getUrl()).pathSegment(cep).build().toUri();
        try {
            ResponseEntity<BrasilApiResponse> response = restTemplate.getForEntity(uri,BrasilApiResponse.class);
            if (!response.getStatusCode().is2xxSuccessful() && response.getBody() == null) {
                throw new RuntimeException("Resposta inválida da API externa.");
            }
            return searchPlaceMapper.toSearchPlaceResponse(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("CEP não encontrado: " + cep);
        } catch (RestClientException e) {
            throw new RuntimeException("Erro ao se comunicar com a API de CEP: " + e.getMessage());
        }
    }

    @Override
    public PlaceResponse insertNewPlace(NewPlaceRequest request) {
        SearchPlaceResponse searchPlaceResponse = searchPlace(request.getCep());
        try {
            Place newPlace = repository.insert(new Place(searchPlaceResponse, request.getNumero(), request.getAvaliacao()));
            return placeMapper.toPlaceResponse(newPlace);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível inserir o novo registro na base de dados.");
        }
    }
}