package com.trucaller.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionsResponse implements Serializable {
    private String requestTransactionId;
    private String responseTransactionId;
    private Integer statusCode;
    private String message;
}
