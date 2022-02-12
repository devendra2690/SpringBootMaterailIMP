package com.devendrabrain.OrgMgr.bo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class EventHttpLogBO {

    private String requestId;
    private String traceId;
    private String spanId;
    private String apiName;
    private String requestBody;
    private String responseBody;
    private Date createDate;
    private String host;
    private String userAgent;
    private String contentType;
}
