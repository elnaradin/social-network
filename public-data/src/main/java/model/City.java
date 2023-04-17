package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class City {
    @Id
    private int id;

    private boolean isDeleted;

    private String title;

    private int countryId;
}
