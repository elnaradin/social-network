package ru.itgroup.intouch.aggregator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

    @GetMapping(value = "/country", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCountries(){
        String s = client.feignGetCountries();
        System.out.println(" ");
        return s;
    }

    @GetMapping(value = "/country/{countryId}/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCities(@PathVariable Long countryId){
        return client.feignGetCities(countryId);
    }
}
