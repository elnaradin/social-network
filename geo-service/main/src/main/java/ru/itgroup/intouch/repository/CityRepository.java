package ru.itgroup.intouch.repository;

import ru.itgroup.intouch.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByCountryId(Long countryId);
}
