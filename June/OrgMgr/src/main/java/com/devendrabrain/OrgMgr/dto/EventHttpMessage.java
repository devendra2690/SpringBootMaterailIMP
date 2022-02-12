package com.devendrabrain.OrgMgr.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"EventDate", "ApiName", "RequestId",
        "Status", "TraceId", "SpanId", "ParentId", "RequestUrl", "RequestMethod", "RequestHeaders", "RequestBody",
        "RequestParameters", "ResponseStatus", "ResponseBody", "ResponseHeaders", "ResponseTimeInMilliSec", "ServletPath", "RemoteAddress", "RemoteHost", "RemotePort", "IPAddress", "UserAgent"})
public class EventHttpMessage {

    @JsonProperty("EventDate")
    private String eventDate;

    @JsonProperty("ApiName")
    private String apiName;

    @JsonProperty("RequestId")
    private String requestId;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("TraceId")
    private String traceId;

    @JsonProperty("SpanId")
    private String spanId;

    @JsonProperty("ParentId")
    private String parentId;

    @JsonProperty("RequestUrl")
    private String requestUrl;

    @JsonProperty("RequestMethod")
    private String requestMethod;

    @JsonProperty("ServletPath")
    private String ServletPath;

    @JsonProperty("RequestHeaders")
    private Map<String, String> requestHeaders;

    @JsonProperty("RequestBody")
    private String requestBody;

    @JsonProperty("RequestParameters")
    private String requestParameters;

    @JsonProperty("ResponseStatus")
    private Integer responseStatus;

    @JsonProperty("ResponseBody")
    private String responseBody;

    @JsonProperty("RemoteAddress")
    private String remoteAddress;

    @JsonProperty("RemoteHost")
    private String remoteHost;

    @JsonProperty("RemotePort")
    private String remotePort;

    @JsonProperty("IPAddress")
    private String iPAddress;

    @JsonProperty("UserAgent")
    private String userAgent;

    @JsonProperty("ResponseHeaders")
    private Map<String, String> responseHeaders;

    @JsonProperty("ResponseTimeInMilliSec")
    private Long responseTimeInMilliSeconds;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getiPAddress() {
        return iPAddress;
    }

    public void setiPAddress(String iPAddress) {
        this.iPAddress = iPAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(String remotePort) {
        this.remotePort = remotePort;
    }

    public String getServletPath() {
        return ServletPath;
    }

    public void setServletPath(String servletPath) {
        ServletPath = servletPath;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(String requestParameters) {
        this.requestParameters = requestParameters;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Map<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public Long getResponseTimeInMilliSeconds() {
        return responseTimeInMilliSeconds;
    }

    public void setResponseTimeInMilliSeconds(Long responseTimeInMilliSeconds) {
        this.responseTimeInMilliSeconds = responseTimeInMilliSeconds;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
