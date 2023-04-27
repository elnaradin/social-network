package dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SearchIds {

    List<Long> ids;

    public List<Long> getIds() {
        if (Objects.isNull(this.ids)) return new ArrayList<>();
        return this.ids;
    }
}
