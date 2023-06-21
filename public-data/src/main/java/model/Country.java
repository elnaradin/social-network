package model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "countries")
public class Country {
    @Id
    private long id;

    @JsonProperty("isDeleted")
    private boolean isDeleted;

    private String title;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "country")
    private List<City> cities;

    @JsonGetter("cities")
    @Transient
    public List<String> getCitiesNames() {
        return cities.stream().map(City::getTitle).toList();
    }
}
