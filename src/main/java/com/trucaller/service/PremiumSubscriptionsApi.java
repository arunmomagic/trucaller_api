package com.trucaller.service;

import com.trucaller.payload.PremiumSubscriptionsRequest;
import com.trucaller.payload.PremiumSubscriptionsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PremiumSubscriptionsApi {
    @Autowired
    RestTemplate restTemplate;
    public ResponseEntity<PremiumSubscriptionsResponse> premiumSubscriptionsApi(PremiumSubscriptionsRequest premiumSubscriptionsRequest){
        String url="https://premium.truecaller.com/v0/premium/partner/"+premiumSubscriptionsRequest.getPartnerId()+"/grant";
        return restTemplate.postForEntity(url,premiumSubscriptionsRequest,PremiumSubscriptionsResponse.class);
    }
}
