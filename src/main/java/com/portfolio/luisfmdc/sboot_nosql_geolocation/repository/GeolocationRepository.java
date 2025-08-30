package com.portfolio.luisfmdc.sboot_nosql_geolocation.repository;

import com.portfolio.luisfmdc.sboot_nosql_geolocation.domain.Geolocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeolocationRepository extends MongoRepository<Geolocation, String> {
}
