package com.tuncay.smsservice.service;

import com.tuncay.smsservice.entity.SmsReceiver;

import java.util.Collection;
import java.util.Optional;

public interface SmsReceiverService {

    public Collection<SmsReceiver> getSmsReceivers();

    public SmsReceiver createSmsReceiver(SmsReceiver smsReceiver);

    public Optional<SmsReceiver> getSmsReceiverById(int id);

    public Optional<SmsReceiver> deleteSmsReceiverById(int id);

}
