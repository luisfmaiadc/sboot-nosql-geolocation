package com.portfolio.luisfmdc.sboot_nosql_geolocation.dto;

public record BrasilApiResponse(
        String cep,
        String street,
        String neighborhood,
        String city,
        String state,
        Location location
) {
    public record Location(
            String type,
            Coordinates coordinates
    ) {
        public record Coordinates(
                String latitude,
                String longitude
        ) {}
    }
}

