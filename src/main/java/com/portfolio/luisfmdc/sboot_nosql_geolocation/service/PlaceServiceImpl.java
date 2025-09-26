package com.portfolio.luisfmdc.sboot_nosql_geolocation.service;

import com.portfolio.luisfmdc.model.NewPlaceRequest;
import com.portfolio.luisfmdc.model.PlaceResponse;
import com.portfolio.luisfmdc.model.SearchPlaceResponse;
import com.portfolio.luisfmdc.model.UpdatePlaceRequest;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.config.RouterProperties;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.config.exception.ExternalApiException;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.config.exception.InvalidArgumentException;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.config.exception.ResourceNotFoundException;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.domain.Place;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.dto.BrasilApiResponse;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.mapper.PlaceMapper;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.mapper.SearchPlaceMapper;
import com.portfolio.luisfmdc.sboot_nosql_geolocation.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository repository;
    private final MongoTemplate mongoTemplate;
    private final RouterProperties properties;
    private final RestTemplate restTemplate;
    private final PlaceMapper placeMapper;
    private final SearchPlaceMapper searchPlaceMapper;

    @Override
    public SearchPlaceResponse searchPlaceByCep(String cep) {
        URI uri = UriComponentsBuilder.fromUriString(properties.getBrasilapi().getUrl()).pathSegment(cep).build().toUri();
        try {
            ResponseEntity<BrasilApiResponse> response = restTemplate.getForEntity(uri, BrasilApiResponse.class);
            if (response.getBody() == null) {
                throw new ExternalApiException("A API externa retornou uma resposta vazia para o CEP: " + cep);
            }
            return searchPlaceMapper.toSearchPlaceResponse(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("CEP não encontrado: " + cep);
        } catch (RestClientException e) {
            throw new ExternalApiException("Erro ao se comunicar com a API de CEP. Detalhes: " + e.getMessage());
        }
    }

    @Override
    public PlaceResponse insertNewPlace(NewPlaceRequest request) {
        SearchPlaceResponse searchPlaceResponse = searchPlaceByCep(request.getCep());
        if (searchPlaceResponse.getGeolocalizacao() == null || searchPlaceResponse.getGeolocalizacao().getLatitude() == null
                || searchPlaceResponse.getGeolocalizacao().getLongitude() == null) {
            throw new ResourceNotFoundException("Não foi possível obter os dados de geolocalização do CEP informado.");
        }
        Place newPlace = new Place(searchPlaceResponse, request);
        Place savedPlace = repository.insert(newPlace);
        return placeMapper.toPlaceResponse(savedPlace);
    }

    @Override
    public List<PlaceResponse> searchPlaceByQuery(String nome, String rua, String bairro, String cidade, String estado, Double avaliacao, Double latitude, Double longitude, Integer raio) {
        List<Criteria> criteriaList = buildSearchQuery(nome, rua, bairro, cidade, estado, avaliacao, latitude, longitude, raio);
        criteriaList.add(Criteria.where("active").is(true));

        if (criteriaList.size() <= 1) {
            throw new InvalidArgumentException("É necessário fornecer ao menos um critério de busca.");
        }

        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        List<Place> placeList = mongoTemplate.find(query, Place.class);

        return placeList.stream().map(placeMapper::toPlaceResponse).toList();
    }

    private List<Criteria> buildSearchQuery(String nome, String rua, String bairro, String cidade, String estado, Double avaliacao, Double latitude, Double longitude, Integer raio) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (nome != null && !nome.isBlank()) {
            criteriaList.add(Criteria.where("name").regex(nome, "i"));
        }

        if (rua != null && !rua.isBlank()) {
            criteriaList.add(Criteria.where("street").regex(rua, "i"));
        }

        if (bairro != null && !bairro.isBlank()) {
            criteriaList.add(Criteria.where("neighborhood").regex(bairro, "i"));
        }

        if (cidade != null && !cidade.isBlank()) {
            criteriaList.add(Criteria.where("city").is(cidade));
        }

        if (estado != null && !estado.isBlank()) {
            criteriaList.add(Criteria.where("state").is(estado));
        }

        if (avaliacao != null) {
            criteriaList.add(Criteria.where("rating").gte(avaliacao));
        }

        boolean hasGeolocalizacao = latitude != null && longitude != null && raio != null;

        if (hasGeolocalizacao) {
            Point ponto = new Point(longitude, latitude);
            Distance distancia = new Distance(raio / 1000.0, Metrics.KILOMETERS);
            criteriaList.add(Criteria.where("geolocation").nearSphere(ponto).maxDistance(distancia.getNormalizedValue()));
        }

        return criteriaList;
    }

    @Override
    public PlaceResponse updatePlace(String idPlace, UpdatePlaceRequest updatePlaceRequest) {
        if (updatePlaceRequest.getAtivo() == null && updatePlaceRequest.getAvaliacao() == null) throw new InvalidArgumentException("Payload inválido.");

        Optional<Place> optionalPlace = repository.findById(idPlace);
        if (optionalPlace.isEmpty()) throw new ResourceNotFoundException("Não foi encontrado nenhum local com o id informado.");

        Place place = optionalPlace.get();
        boolean isUpdateValid = updatePlaceData(place, updatePlaceRequest);
        if (isUpdateValid) repository.save(place);

        return placeMapper.toPlaceResponse(place);
    }

    private boolean updatePlaceData(Place place, UpdatePlaceRequest request) {
        boolean isUpdateValid = false;
        if (request.getAvaliacao() != null && !request.getAvaliacao().equals(place.getRating())) {
            place.setRating(request.getAvaliacao());
            isUpdateValid = true;
        }

        if (request.getAtivo() != null && !request.getAtivo().equals(place.getActive())) {
            place.setActive(request.getAtivo());
            isUpdateValid = true;
        }

        return isUpdateValid;
    }
}