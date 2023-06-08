package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountSearchDtoPageable {

    private Long id;

    private boolean isDeleted;

    private List<Long> ids;

    private List<Long> blockedByIds;

    private String author;

    private String firstName;

    private String lastName;

    private String city;

    private String country;

    private boolean isBlocked;

    @JsonProperty(defaultValue = "0")
    private int ageTo;

    @JsonProperty(defaultValue = "0")
    private int ageFrom;

    private String page;

    private String size;

    private String sort;

}


