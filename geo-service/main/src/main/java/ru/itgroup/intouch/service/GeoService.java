package ru.itgroup.intouch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.City;
import model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.itgroup.intouch.model.AreaUnit;
import ru.itgroup.intouch.repository.CityRepository;
import ru.itgroup.intouch.repository.CountryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeoService {

    private static final String GEO_SOURCE = "https://api.hh.ru/areas/113";
    @Autowired
    CityRepository cityRepository;
    @Autowired
    CountryRepository countryRepository;

    private List<Long> loadedCityID = new ArrayList<>();

    @Scheduled(cron = "${loadGeoPeriod-in-cron}")
    public ResponseEntity loadGeo() {
        try {
            System.out.println("loading started");
            AreaUnit areaUnit = getAreaUnits();
            Country country = createCountry(areaUnit);
            countryRepository.saveAndFlush(country);
            List<AreaUnit> citiesUnits = areaUnit.getAreas().stream().flatMap(a -> a.getAreas().stream()).toList();
            List<City> cities = new ArrayList<>();
            citiesUnits.forEach(cu -> {
                long cityID = Long.parseLong(cu.getId());
                if(!loadedCityID.contains(cityID)) {
                    loadedCityID.add(cityID);
                    City city = createCity(cu, country);
                    cities.add(city);
                }
            });
            cityRepository.saveAll(cities);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    public List<City> getCities(Long countryId) {
        Optional<Country> optionalCountry = countryRepository.findById(countryId);
        return optionalCountry.map(country -> cityRepository.findByCountry(country)).orElse(null);
    }

    private AreaUnit getAreaUnits() throws Exception{
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();
        String response = restTemplate.getForObject(GEO_SOURCE, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, AreaUnit.class);
    }

    private Country createCountry(AreaUnit countryUnit) {
        Country country = new Country();
        country.setId(Long.parseLong(countryUnit.getId()));
        country.setTitle(countryUnit.getName());
        country.setDeleted(false);
        return country;
    }

    private City createCity(AreaUnit cityUnit, Country country) {
        City city = new City();
        city.setId(Long.parseLong(cityUnit.getId()));
        city.setDeleted(false);
        city.setTitle(cityUnit.getName());
        city.setCountry(country);
        return city;
    }
}
