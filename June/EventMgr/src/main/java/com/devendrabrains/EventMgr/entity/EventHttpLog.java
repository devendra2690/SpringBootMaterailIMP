package com.devendrabrains.EventMgr.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "App")
@Builder
@Data
public class EventHttpLog {

    private static final long serialVersionUID = -5363763670010877273L;

    @javax.persistence.Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    private Long Id;

    @Column(name = "event_date")
    private Date eventDate;

    @Column(name = "api_name")
    private String apiName;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "Status")
    private String status;

    @Column(name = "trace_id")
    private String traceId;

    @Column(name = "span_id")
    private String spanId;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "request_url")
    private String requestUrl;

    @Column(name = "request_method")
    private String requestMethod;

    @Column(name = "request_headers")
    private String requestHeaders;

    @Column(name = "request_body")
    private String requestBody;

    @Column(name = "request_parameters")
    private String requestParameters;

    @Column(name = "response_status")
    private Integer responseStatus;

    @Column(name = "response_body")
    private String responseBody;

    @Column(name = "response_headers")
    private String responseHeaders;

    @Column(name = "remote_address")
    private String remoteAddress;

    @Column(name = "remote_host")
    private String remoteHost;

    @Column(name = "remote_port")
    private String remotePort;

    @Column(name = "servlet_path")
    private String servletPath;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "ipaddress")
    private String IPAddress;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;
}
