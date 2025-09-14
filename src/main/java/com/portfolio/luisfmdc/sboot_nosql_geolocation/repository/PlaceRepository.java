package com.portfolio.luisfmdc.sboot_nosql_geolocation.repository;

import com.portfolio.luisfmdc.sboot_nosql_geolocation.domain.Place;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends MongoRepository<Place, String> {
}
