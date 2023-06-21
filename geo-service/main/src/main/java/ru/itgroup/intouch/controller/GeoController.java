package ru.itgroup.intouch.controller;


import model.City;
import model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.service.GeoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/geo")
public class GeoController {
    @Autowired
    GeoService geoService;

    @PutMapping("/load")
    public ResponseEntity<?> loadGeo(){
        geoService.loadGeo();
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/country", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Country>> getCountries(){
        List<Country> countries = geoService.getCountries();
        return ResponseEntity.ok(countries);
    }

    @GetMapping(value = "/country/{countryId}/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<City>> getCities(@PathVariable Long countryId){
        return ResponseEntity.ok(geoService.getCities(countryId));
    }
}
