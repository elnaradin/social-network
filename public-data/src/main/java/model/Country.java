package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Country {
    @Id
    private Long id;

    private boolean isDeleted;

    private String title;

    @OneToMany(mappedBy = "countryId")
    private List<City> cities;
}
