package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cities")
public class City {
    @Id
    private long id;

    @JsonProperty("isDeleted")
    private boolean isDeleted;

    private String title;

    @ManyToOne()
    @JoinColumn(name = "country_id", nullable = false)
    @JsonBackReference
    private Country country;

    @JsonGetter("countryId")
    @Transient
    public Long getCountryId() {
        return country.getId();
    }
}
