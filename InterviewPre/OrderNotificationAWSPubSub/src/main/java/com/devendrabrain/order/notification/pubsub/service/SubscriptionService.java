package com.devendrabrain.order.notification.pubsub.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    @Autowired
    AmazonSNSClient amazonSNSClient;

    public void subscribeToSNS(String SNS_ARN, List<String> emails) {

        // Fetch Already Subscribed endpoint
        List<String> subscriptions = amazonSNSClient.listSubscriptionsByTopic(SNS_ARN)
                .getSubscriptions().stream().map(Subscription::getEndpoint)
                .collect(Collectors.toList());

        // do not again subscribed those who already subscribed
        List<String> newEmailList = emails.stream()
                                      .filter(email -> !subscriptions.contains(email))
                                      .collect(Collectors.toList());

        for (String email : newEmailList) {
            SubscribeRequest subscribeRequest =
                    new SubscribeRequest(SNS_ARN,"email",email);
            amazonSNSClient.subscribe(subscribeRequest);
        }
    }

}
