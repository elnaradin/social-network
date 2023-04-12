package model;

import lombok.Data;
import model.City;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Country {

    @Id
    private int id;

    private boolean isDeleted;

    private String title;

    @OneToMany (mappedBy = "Country")
    private List<City> cities;
}
