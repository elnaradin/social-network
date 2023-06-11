package ru.itgroup.intouch.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.model.Country;
import ru.itgroup.intouch.service.GeoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/geo")
public class GeoController {
    @Autowired
    GeoService geoService;

    @PutMapping("/load")
    public ResponseEntity loadGeo(){
        geoService.loadGeo();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/country")
    public ResponseEntity getCountries(){
        List<Country> countries = geoService.getCountries();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/country/{countryId}/cities")
    public ResponseEntity getCities(@PathVariable Long countryId){
        return ResponseEntity.ok(geoService.getCities(countryId));
    }
}
