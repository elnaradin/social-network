package ru.itgroup.intouch.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "countries")
public class Country {
    @Id
    private long id;

    private boolean isDeleted;

    private String title;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "country")
    private List<City> cities;

    @JsonGetter("cities")
    @Transient
    public List<String> getCitiesNames(){
        return cities.stream().map(City::getTitle).toList();
    }
}
