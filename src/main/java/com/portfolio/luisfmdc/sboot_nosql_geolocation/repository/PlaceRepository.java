package com.portfolio.luisfmdc.sboot_nosql_geolocation.repository;

import com.portfolio.luisfmdc.sboot_nosql_geolocation.domain.Place;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends MongoRepository<Place, String> {

    List<Place> findByNameContainingIgnoreCase(String nome);
}
