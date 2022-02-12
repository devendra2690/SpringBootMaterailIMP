package com.devendrabrain.OrgMgr.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentBO {

    private Long id;
    private String name;
    private String city;
    private String classroom;
}
