package ru.itgroup.intouch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.itgroup.intouch.model.AreaUnit;
import ru.itgroup.intouch.model.City;
import ru.itgroup.intouch.model.Country;
import ru.itgroup.intouch.repository.CityRepository;
import ru.itgroup.intouch.repository.CountryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeoService {

    private static final String GEO_SOURCE = "https://api.hh.ru/areas/113";
    @Autowired
    CityRepository cityRepository;
    @Autowired
    CountryRepository countryRepository;

    public ResponseEntity loadGeo() {
        try {
            AreaUnit areaUnit = getAreaUnits();
            Country country = new Country();
            country.setId(Long.parseLong(areaUnit.getId()));
            country.setTitle(areaUnit.getName());
            country.setDeleted(false);
            countryRepository.saveAndFlush(country);
            List<AreaUnit> citiesUnits = areaUnit.getAreas().stream().flatMap(a -> a.getAreas().stream()).toList();
            List<City> cities = new ArrayList<>();
            citiesUnits.forEach(cu -> {
                City city = new City();
                city.setId(Long.parseLong(cu.getId()));
                city.setDeleted(false);
                city.setTitle(cu.getName());
                city.setCountry(country);
                cities.add(city);
            });
            cityRepository.saveAll(cities);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private AreaUnit getAreaUnits() throws Exception{
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();
        String response = restTemplate.getForObject(GEO_SOURCE, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, AreaUnit.class);
    }

    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    public List<City> getCities(Long countryId) {
        if(countryRepository.findById(countryId).isEmpty()){
            return null;
        }
        return cityRepository.findByCountryId(countryId);
    }
}
