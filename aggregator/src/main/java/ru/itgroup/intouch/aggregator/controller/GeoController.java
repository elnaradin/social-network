package ru.itgroup.intouch.aggregator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.client.GeoServiceClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/geo")
public class GeoController {

    private final GeoServiceClient client;

    @PutMapping("/load")
    public ResponseEntity loadGeo(){
        return client.feignLoadGeo();
    }

    @GetMapping("/country")
    public ResponseEntity getCountries(){
        return client.feignGetCountries();
    }

    @GetMapping("/country/{countryId}/cities")
    public ResponseEntity getCities(@PathVariable Long countryId){
        return client.feignGetCities(countryId);
    }
}
