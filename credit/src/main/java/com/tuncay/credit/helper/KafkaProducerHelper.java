package com.tuncay.credit.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tuncay.credit.domain.CreditSmsMessage;

public interface KafkaProducerHelper {

    public void sentSmsMessageToKafkaTopic(CreditSmsMessage smsMessage) throws JsonProcessingException;

}
