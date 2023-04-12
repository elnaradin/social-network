package model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class City {
    @Id
    private int id;

    private boolean isDeleted;

    private String title;

    private int countryId;
}
