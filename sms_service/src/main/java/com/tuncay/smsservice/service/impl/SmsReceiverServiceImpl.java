package com.tuncay.smsservice.service.impl;

import com.tuncay.smsservice.entity.SmsReceiver;
import com.tuncay.smsservice.repository.SmsReceiverDAO;
import com.tuncay.smsservice.service.SmsReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class SmsReceiverServiceImpl implements SmsReceiverService {

    @Autowired
    private SmsReceiverDAO smsReceiverDAO;

    public Collection<SmsReceiver> getSmsReceivers() {
        return smsReceiverDAO.getSmsReceivers();
    }

    public SmsReceiver createSmsReceiver(SmsReceiver smsReceiver) {
        return smsReceiverDAO.createSmsReceiver(smsReceiver);
    }

    public Optional<SmsReceiver> getSmsReceiverById(int id) {
        return smsReceiverDAO.getSmsReceiverById(id);
    }

    public Optional<SmsReceiver> deleteSmsReceiverById(int id) {
        return smsReceiverDAO.deleteSmsReceiverById(id);
    }

}
