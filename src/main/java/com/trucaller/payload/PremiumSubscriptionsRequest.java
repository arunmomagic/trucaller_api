package com.trucaller.payload;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremiumSubscriptionsRequest implements Serializable {
    private String partnerId;
    private Long customer;
    private String premiumId;
    private String transactionId;
    private String signature;
}
