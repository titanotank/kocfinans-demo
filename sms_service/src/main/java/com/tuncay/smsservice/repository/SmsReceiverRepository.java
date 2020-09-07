package com.tuncay.smsservice.repository;

import com.tuncay.smsservice.entity.SmsReceiver;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SmsReceiverRepository extends MongoRepository<SmsReceiver,Integer> {
}

