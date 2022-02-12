package com.devendrabrains.EventMgr.listner;

import com.devendrabrains.EventMgr.dto.EventHttpMessage;
import com.devendrabrains.EventMgr.entity.EventHttpLog;
import com.devendrabrains.EventMgr.repository.EventHttpLogRepository;
import com.devendrabrains.EventMgr.util.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Date;

public class EventHttpLogMessageListener implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(EventHttpLogMessageListener.class);

    private EventHttpLogRepository eventHttpLogRepository;

    public EventHttpLogMessageListener(EventHttpLogRepository eventHttpLogRepository) {

        this.eventHttpLogRepository = eventHttpLogRepository;
    }

    @Override
    public void onMessage(Message message) {

        EventHttpMessage eventLog = new EventHttpMessage();
        String json = new String(message.getBody());
        LOG.debug("Event Log ", json);
        try {
            eventLog = JSONUtils.toObject(json, EventHttpMessage.class);
        } catch (IOException e) {
            LOG.error("Failed to convert Json to Object", e);
        }
        if (eventLog != null) {
            try {

                EventHttpLog eventHttpLog = trasformToEventHttpLog(eventLog);
                LOG.debug("Event Http Log Entity:" + JSONUtils.toJson(eventHttpLog));
                eventHttpLogRepository.save(eventHttpLog);
            } catch (Exception e) {
                LOG.error("Failed saving in data base", e);
            }
        }
    }

    private EventHttpLog trasformToEventHttpLog(EventHttpMessage eventLog) {

        EventHttpLog.EventHttpLogBuilder eventHttpLog = EventHttpLog.builder();

        if(eventLog != null) {

            eventHttpLog.eventDate(new Date());
            eventHttpLog.createDate(new Date());

            if(eventLog.getRemoteHost() != null) {
                eventHttpLog.remoteHost(eventLog.getRemoteHost());
            }

            if(eventLog.getRemoteAddress() != null) {
                eventHttpLog.remoteAddress(eventLog.getRemoteAddress());
            }

            if(eventLog.getServletPath() != null) {
                eventHttpLog.servletPath(eventLog.getServletPath());
            }

            if(eventLog.getRequestId() != null) {
                eventHttpLog.requestId(eventLog.getRequestId());
            }

            if(eventLog.getTraceId() != null) {
                eventHttpLog.traceId(eventLog.getTraceId());
            }

            if(eventLog.getSpanId() != null) {
                eventHttpLog.spanId(eventLog.getSpanId());
            }

            if(eventLog.getParentId() != null) {
                eventHttpLog.parentId(eventLog.getParentId());
            }

            if(eventLog.getApiName() != null) {
                eventHttpLog.apiName(eventLog.getApiName());
            }

            if(eventLog.getRequestBody() != null) {
                eventHttpLog.requestBody(eventLog.getRequestBody());
            }

            if(eventLog.getResponseBody() != null) {
                eventHttpLog.responseBody(eventLog.getResponseBody());
            }

            if(eventLog.getRequestUrl() != null) {
                eventHttpLog.requestUrl(eventLog.getRequestUrl());
            }

            if(eventLog.getRequestMethod() != null) {
                eventHttpLog.requestMethod(eventLog.getRequestMethod());
            }

            if(!CollectionUtils.isEmpty(eventLog.getRequestHeaders())) {
                eventHttpLog.requestHeaders(eventLog.getRequestHeaders().toString());
            }
        }


        return eventHttpLog.build();
    }


}
