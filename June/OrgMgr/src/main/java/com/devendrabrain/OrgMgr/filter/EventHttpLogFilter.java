package com.devendrabrain.OrgMgr.filter;

import com.devendrabrain.OrgMgr.constant.AmqType;
import com.devendrabrain.OrgMgr.dto.EventHttpMessage;
import com.devendrabrain.OrgMgr.utils.HttpServletUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class EventHttpLogFilter extends OncePerRequestFilter {

    private static Logger log = LoggerFactory.getLogger(EventHttpLogFilter.class);

    private RabbitTemplate rabbitTemplate;
    private Environment environment;

    public EventHttpLogFilter(RabbitTemplate rabbitTemplate, Environment environment) {
        this.rabbitTemplate = rabbitTemplate;
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        httpServletRequest = new RequestWrapper(httpServletRequest);
        httpServletResponse = new ResponseWrapper(httpServletResponse);

        Long startTime = System.currentTimeMillis();
        Map<String, String> requestMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        requestMap.putAll(HttpServletUtils.getHeadersInfo(httpServletRequest));

        // Another way to get it
   /*     Map<String, List<String>> headersMap = Collections.list(httpServletRequest.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        h -> Collections.list(httpServletRequest.getHeaders(h))
                ));*/

        String requestId = null;
        if (StringUtils.isNotBlank(requestMap.get("X-RS-RequestId"))) {
            requestId = requestMap.get("X-RS-RequestId");
        } else {
            requestId = UUID.randomUUID().toString();
        }
        try {

            MDC.put("X-RS-RequestId", requestId);
            MDC.put("X-RS-SourceApplication", environment.getProperty("spring.application.name"));
            if (StringUtils.isNotBlank(requestMap.get("x-b3-traceid"))) {
                MDC.put("x-b3-traceid", requestMap.get("x-b3-traceid"));
            } else {
                MDC.put("x-b3-traceid", requestId);
            }

            if (StringUtils.isNotBlank(requestMap.get("x-b3-spanid"))) {
                MDC.put("x-b3-spanid", requestMap.get("x-b3-spanid"));
            } else {
                MDC.put("x-b3-spanid", requestId);
            }

            if (StringUtils.isNotBlank(requestMap.get("x-b3-parentspanid"))) {
                MDC.put("x-b3-parentspanid", requestMap.get("x-b3-parentspanid"));
            } else {
                MDC.put("x-b3-parentspanid", requestId);
            }

            if (StringUtils.isNotBlank(requestMap.get("user-agent"))) {
                MDC.put("UserAgent", requestMap.get("user-agent"));
            }

            if (requestMap.containsKey("x-real-ip") && StringUtils.isNotBlank(requestMap.get("x-real-ip"))) {
                MDC.put("IPAddress", requestMap.get("x-real-ip"));
            }

            if (StringUtils.isNotBlank(requestMap.get("Origin"))) {
                MDC.put("Origin", requestMap.get("Origin"));
            }
            if (StringUtils.isNotBlank(requestMap.get("Referer"))) {
                MDC.put("Referer", requestMap.get("Referer"));
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            try {
                Long elapsed = System.currentTimeMillis() - startTime;
                EventHttpMessage eventHttpMessage = new EventHttpMessage();

                eventHttpMessage.setRequestId(requestId);

                if (StringUtils.isNotBlank(MDC.get("x-b3-traceid"))) {
                    eventHttpMessage.setTraceId(requestMap.get("x-b3-traceid"));
                }

                if (StringUtils.isNotBlank(MDC.get("x-b3-parentspanid"))) {
                    eventHttpMessage.setParentId(MDC.get("x-b3-parentspanid"));
                }


                if (StringUtils.isNotBlank(MDC.get("x-b3-spanid"))) {
                    eventHttpMessage.setSpanId(MDC.get("x-b3-spanid"));
                }

                if (StringUtils.isNotBlank(MDC.get("X-RS-ApiName"))) {
                    eventHttpMessage.setApiName(MDC.get("X-RS-ApiName"));
                }

                if (StringUtils.isNotBlank(MDC.get("UserAgent"))) {
                    eventHttpMessage.setUserAgent(MDC.get("UserAgent"));
                }

                if (StringUtils.isNotBlank(MDC.get("IPAddress"))) {
                    eventHttpMessage.setiPAddress(MDC.get("IPAddress"));
                }


                // Set to HttpEvent
               // EventHttpTransformer.setEventHttp(requestMap, eventHttpMessage, environment);
                if (httpServletRequest.getUserPrincipal() != null
                        && StringUtils.isNotBlank(httpServletRequest.getUserPrincipal().getName())) {
                    //eventHttpMessage.principle(httpServletRequest.getUserPrincipal().getName());
                }

                if (StringUtils.isNotBlank(httpServletRequest.getRemoteAddr())) {
                    eventHttpMessage.setRemoteAddress(httpServletRequest.getRemoteAddr());
                }
                if (StringUtils.isNotBlank(httpServletRequest.getRemoteHost())) {
                    eventHttpMessage.setRemoteHost(httpServletRequest.getRemoteHost());
                }
                eventHttpMessage.setRemotePort(Integer.toString(httpServletRequest.getRemotePort()));

                eventHttpMessage.setRequestUrl(HttpServletUtils.getURL(httpServletRequest));

                if (StringUtils.isNotBlank(httpServletRequest.getMethod())) {
                    eventHttpMessage.setRequestMethod(httpServletRequest.getMethod());
                }

                eventHttpMessage.setRequestBody(HttpServletUtils.getRequestBody(httpServletRequest, requestMap));

                if (!CollectionUtils.isEmpty(httpServletRequest.getParameterMap())) {
                    eventHttpMessage.setRequestParameters(RestUtils.requestParamsString(httpServletRequest.getParameterMap()));

                }

                  if (StringUtils.isNotBlank(httpServletRequest.getServletPath())) {
                    eventHttpMessage.setServletPath(httpServletRequest.getServletPath());
                }

                eventHttpMessage.setResponseStatus(httpServletResponse.getStatus());
                Map<String, String> responseMap = HttpServletUtils.getHeadersInfo(httpServletResponse);
                eventHttpMessage.setResponseHeaders(responseMap);
                eventHttpMessage.setResponseBody(HttpServletUtils.getResponseBody(httpServletResponse, responseMap));
                /*
                 * Send message to RabbitMQ
                 */
                log.info("Event log filter before sending to rabbitmq");
                rabbitTemplate.convertAndSend(AmqType.eventHttpLogDirectExchange.getValue(),
                        AmqType.eventHttpLogBindingBuilder.getValue(), eventHttpMessage);
                log.info("Event log filter after sending to rabbitmq");

            } catch (Exception e) {
                log.error("ClassName: " + this.getClass().getSimpleName(), e);
            }
            MDC.clear();
        }

    }
}
