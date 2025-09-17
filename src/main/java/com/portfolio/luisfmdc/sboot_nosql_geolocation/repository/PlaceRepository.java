package com.portfolio.luisfmdc.sboot_nosql_geolocation.repository;

import com.portfolio.luisfmdc.sboot_nosql_geolocation.domain.Place;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends MongoRepository<Place, String> {

    List<Place> findByNameContainingIgnoreCaseAndActiveTrue(String nome);
    List<Place> findByLocationNear(Point point, Distance distance);
}
