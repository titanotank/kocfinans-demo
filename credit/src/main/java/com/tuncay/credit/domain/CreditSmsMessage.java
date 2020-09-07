package com.tuncay.credit.domain;

import com.tuncay.credit.entity.CreditResult;
import lombok.*;

import java.io.Serializable;

// Kafka Topic Message

@Data
@ToString
public class CreditSmsMessage implements Serializable {

    private String identity_number;
    private CreditResult creditResult;
    private int limit;


}
