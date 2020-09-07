package com.tuncay.smsservice.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

// Kafka Topic Message

@Data
@ToString
public class CreditSmsMessage implements Serializable {

    private String identity_number;
    private CreditResult creditResult;
    private int limit;


}
