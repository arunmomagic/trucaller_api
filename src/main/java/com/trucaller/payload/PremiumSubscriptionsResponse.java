package com.trucaller.payload;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremiumSubscriptionsResponse implements Serializable {
    private String requestTransactionId;
    private String responseTransactionId;
}
