package com.tuncay.smsservice.repository;

import com.tuncay.smsservice.entity.SmsReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class SmsReceiverDAO {
    @Autowired
    private SmsReceiverRepository repository;

    public Collection<SmsReceiver> getSmsReceivers() {
        return repository.findAll();
    }

    public SmsReceiver createSmsReceiver(SmsReceiver smsReceiver) {
        return repository.insert(smsReceiver);
    }

    public Optional<SmsReceiver> getSmsReceiverById(int id) {
        return repository.findById(id);
    }

    public Optional<SmsReceiver> deleteSmsReceiverById(int id) {
        Optional<SmsReceiver> smsReceiver = repository.findById(id);
        smsReceiver.ifPresent(b -> repository.delete(b));
        return smsReceiver;
    }
}
