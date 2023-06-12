package ru.itgroup.intouch.repository;

import model.City;
import model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByCountry(Country country);
}
