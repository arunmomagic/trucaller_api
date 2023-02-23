package com.trucaller.payload;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionsRequest implements Serializable {
    private String msisdn;
    private Integer days;
}
