package com.trucaller.service;

import com.google.gson.Gson;
import com.trucaller.common.Defs;
import com.trucaller.common.Utils;
import com.trucaller.common.enums.PremiumId;
import com.trucaller.common.exception.CryptoUtils;
import com.trucaller.payload.PremiumSubscriptionsRequest;
import com.trucaller.payload.PremiumSubscriptionsResponse;
import com.trucaller.payload.SubscriptionsRequest;
import com.trucaller.payload.SubscriptionsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.*;
import java.util.Date;
import java.util.UUID;

@Service
public class PremiumSubscriptionsService {
    Logger logger = LoggerFactory.getLogger(PremiumSubscriptionsService.class);
    @Autowired
    PremiumSubscriptionsApi premiumSubscriptionsApi;

    public SubscriptionsResponse premiumSubscriptions(SubscriptionsRequest subscriptionsRequest) throws IOException {

        String filePath = Utils.getDateToString(new Date(), Defs.logDate) + "_request_response.log";
        FileWriter fileWriter = new FileWriter(filePath,true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        try {
            String partnerId = Defs.partnerId;
            Long customer = Long.valueOf(subscriptionsRequest.getMsisdn());
            String premiumId = " ";
            if (Utils.isOk(subscriptionsRequest.getDays()) && (subscriptionsRequest.getDays() == PremiumId.momagic_1.getValue() || subscriptionsRequest.getDays() == PremiumId.momagic_3.getValue() || subscriptionsRequest.getDays() == PremiumId.momagic_7.getValue() || subscriptionsRequest.getDays() == PremiumId.momagic_15.getValue() || subscriptionsRequest.getDays() == PremiumId.momagic_30.getValue())) {
                premiumId = PremiumId.getById(subscriptionsRequest.getDays()).getDisplayName();
            } else {
                return SubscriptionsResponse.builder()
                        .message("Day is valid for only  1,3,7,15 or 30")
                        .statusCode(600)
                        .build();
            }
            String transactionId =UUID.randomUUID().toString();
            // Create the message
            String signature = CryptoUtils.sign(CryptoUtils.prepareDataArray(partnerId, customer, premiumId, transactionId));
            PremiumSubscriptionsRequest premiumSubscriptionsRequest = PremiumSubscriptionsRequest.builder()
                    .customer(customer)
                    .partnerId(partnerId)
                    .premiumId(premiumId)
                    .transactionId(transactionId)
                    .signature(signature)
                    .build();
            String request = " Request{} :: " + new Gson().toJson(premiumSubscriptionsRequest);
            logger.info(request);
            bufferedWriter.write(new Date()+" "+request);
            bufferedWriter.newLine();
            Boolean status= Utils.verifiedSignature(premiumSubscriptionsRequest.getSignature(),partnerId+customer+premiumId+transactionId);
            System.out.println(status);
            ResponseEntity<PremiumSubscriptionsResponse> response = premiumSubscriptionsApi.premiumSubscriptionsApi(premiumSubscriptionsRequest);
            SubscriptionsResponse subscriptionsResponse = SubscriptionsResponse.builder()
                    .responseTransactionId(response.getBody().getResponseTransactionId())
                    .requestTransactionId(response.getBody().getRequestTransactionId())
                    .message("Ok")
                    .statusCode(response.getStatusCodeValue())
                    .build();
            String res = "response{} :: " + new Gson().toJson(subscriptionsResponse);
            logger.info(res);
            bufferedWriter.write(new Date()+" "+res);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
            return subscriptionsResponse;
        } catch (HttpClientErrorException.BadRequest ex) {
            SubscriptionsResponse subscriptionsResponse = SubscriptionsResponse.builder()
                    .message(Defs.BadRequest_MSG)
                    .statusCode(Defs.BadRequest)
                    .build();
            String res = "response{} :: " + new Gson().toJson(subscriptionsResponse);
            bufferedWriter.write(new Date()+" "+res);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
            return subscriptionsResponse;
        } catch (HttpClientErrorException.Unauthorized ex) {
            SubscriptionsResponse subscriptionsResponse = SubscriptionsResponse.builder()
                    .message(Defs.Unauthorized_MSG)
                    .statusCode(Defs.Unauthorized)
                    .build();
            String res = "response{} :: " + new Gson().toJson(subscriptionsResponse);
            bufferedWriter.write(new Date()+" "+res);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
            return subscriptionsResponse;
        } catch (HttpClientErrorException.Conflict ex) {
            SubscriptionsResponse subscriptionsResponse = SubscriptionsResponse.builder()
                    .message(Defs.Conflict_MSG)
                    .statusCode(Defs.Conflict)
                    .build();
            String res = "response{} :: " + new Gson().toJson(subscriptionsResponse);
            bufferedWriter.write(new Date()+" "+res);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
            return subscriptionsResponse;
        } catch (Exception ex) {
            SubscriptionsResponse subscriptionsResponse = SubscriptionsResponse.builder()
                    .message(ex.getMessage())
                    .statusCode(Defs.InternalServerError)
                    .build();
            String res = "response{} :: " + new Gson().toJson(subscriptionsResponse);
            bufferedWriter.write(new Date()+" "+res);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
            return subscriptionsResponse;
        }
    }
}
