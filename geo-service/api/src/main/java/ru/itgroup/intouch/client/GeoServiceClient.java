package ru.itgroup.intouch.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(
        name = "geo-service",
        url = "${SN_GEO_HOST}:${SN_GEO_PORT}",
        path = "/api/v1/geo"
)
public interface GeoServiceClient {

    @PutMapping("/load")
    ResponseEntity feignLoadGeo();

    @GetMapping("/country")
    public ResponseEntity feignGetCountries();

    @GetMapping("/country/{countryId}/cities")
    public ResponseEntity feignGetCities(@PathVariable Long countryId);
}
