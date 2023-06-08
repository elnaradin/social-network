package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostSearchDtoPageable {
    private long id;

    private boolean isDeleted;

    private List<Long> ids;

    private List<Long> blockedIds;

    private List<Long> accountIds;

    private String author;

    private boolean withFriends;

    private List<String> tags;

    private String dateFrom;

    private String dateTo;

    private String page;

    private String size;

    private String sort;
}
