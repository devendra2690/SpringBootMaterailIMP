package com.devendrabrain.OrderManagement.util.notification.pubsub;

import com.devendrabrain.OrderManagement.dto.NotificationDTO;
import com.devendrabrain.OrderManagement.util.billing.BillingDTO;
import com.devendrabrain.OrderManagement.util.customermgr.CustomerDTO;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class PubSubServiceUtil {

    @Autowired
    Environment environment;

    public ResponseEntity<String> publishNotification(NotificationDTO notificationDTO) {

        HttpHeaders headers = new HttpHeaders();
        /*
         * Setting Request in headers for backtracking
         */
        headers.set("X-RS-RequestId", MDC.get("X-RS-TraceId"));
        /*
         * Set Source Application in headers, so that we can save it
         * EventHttpLog, which application we are calling
         */
        headers.set("X-RS-SourceApplication", "OrderNotificationAWSPubSub");
        headers.set("X-RS-ApiName", "Outbound_OrderNotificationAWSPubSub_PublishNotification");

        HttpEntity<NotificationDTO> requestEntity = new HttpEntity<NotificationDTO>(notificationDTO,
                headers);
        ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
        };
        ResponseEntity<String> responseEntity = null;

        try {
            String url = "http://localhost:8781/post/groupnotification";
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl(url);
            RestTemplate restTemplate = new RestTemplate();
            responseEntity = restTemplate
                    .exchange(
                            builder.toUriString(),
                            HttpMethod.POST, requestEntity, typeRef);
        } catch (RestClientException | IllegalStateException  e) {
        } catch (Exception e) {
            e.printStackTrace();
        }


        return responseEntity;
    }
}
