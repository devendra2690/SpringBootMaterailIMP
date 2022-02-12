package com.devendrabrain.OrgMgr.constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum AmqType {

    eventHttpLogQueue("ampq.queue.eventHttplog"),
    eventHttpLogDirectExchange("amqp.exchange.direct.eventHttplog"),
    eventHttpLogBindingBuilder("amqp.routingkey.eventHttplog"),
    eventHttpfilterName("eventHttpLogFilter"),;


    private String value;

    private static final List<String> values;

    static {
        values = new ArrayList<>();
        for (AmqType amqType : AmqType.values()) {
            values.add(amqType.value);
        }
    }

    public String getValue() {
        return value;
    }

    private AmqType(String value) {
        this.value = value;
    }

    public static List<String> getValues() {
        return Collections.unmodifiableList(values);
    }

}
