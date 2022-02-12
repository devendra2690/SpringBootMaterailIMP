package com.devendrabrain.OrgMgr.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@JsonPropertyOrder({"id","name","classroom","city"})
@Data
@Builder
public class StudentDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("classroom")
    private String classRoom;

    @JsonProperty("city")
    private String city;
}
