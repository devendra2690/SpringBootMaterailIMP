package com.devendrabrain.StudentMgr.bo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
    private String principle;
    private String remoteAddress;
    private String remoteHost;
    private String remotePort;
    private String remoteUser;
    private String requestUrl;
    private String requestMethod;
    private Map<String, String > requestHeader = new TreeMap<>();
    private String requestParams;
    private String apiversion;
    private String externalId;
    private String externalRequestId;
    private int responseStatus;
    private Map<String, String> responseHeaders = new HashMap<>();
    private Long responseTimeInMilliSeconds;


}
