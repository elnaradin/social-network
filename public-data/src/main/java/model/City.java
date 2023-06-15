package model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cities")
public class City {
    @Id
    private long id;

    private boolean isDeleted;

    private String title;

    @ManyToOne ()
    @JoinColumn(name = "country_id", nullable = false)
    @JsonIgnore
    private Country country;

    @JsonGetter("countryId")
    @Transient
    public Long getCountryId(){
        return country.getId();
    }
}
