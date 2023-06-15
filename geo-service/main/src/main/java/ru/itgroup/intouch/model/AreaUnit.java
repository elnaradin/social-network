package ru.itgroup.intouch.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.List;

@Data
public class AreaUnit {
    private String id;
    @JsonSetter("parent_id")
    private String parentId;
    private String name;
    private List<AreaUnit> areas;
}
