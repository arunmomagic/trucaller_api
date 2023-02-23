package com.trucaller.controller;

import com.trucaller.payload.SubscriptionsRequest;
import com.trucaller.payload.SubscriptionsResponse;
import com.trucaller.service.PremiumSubscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping(path = "/premium")
public class PremiumSubscriptionsController {
    @Autowired
    private PremiumSubscriptionsService premiumSubscriptionsService;
    @GetMapping(path = "/subscriptions")
    public SubscriptionsResponse createBrand(@Valid SubscriptionsRequest subscriptionsRequest) throws IOException {
         return premiumSubscriptionsService.premiumSubscriptions(subscriptionsRequest);
    }
}
