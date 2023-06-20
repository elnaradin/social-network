package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Tag;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class PostSearchDto implements Serializable {

    private long id;

    private boolean isDeleted;

    private List<Long> ids;

    private List<Long> blockedIds;

    private List<Long> accountIds;

    private String author;

    private boolean withFriends;

    private List<String> tags;

    private ZonedDateTime dateFrom;

    private ZonedDateTime dateTo;


}
