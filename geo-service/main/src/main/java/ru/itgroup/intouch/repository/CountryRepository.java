package ru.itgroup.intouch.repository;

import ru.itgroup.intouch.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
