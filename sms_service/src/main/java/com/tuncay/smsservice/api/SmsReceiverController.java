package com.tuncay.smsservice.api;

import com.tuncay.smsservice.entity.SmsReceiver;
import com.tuncay.smsservice.service.SmsReceiverService;
import com.tuncay.smsservice.service.impl.SmsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/SmsReceivers")
@Api(value="/SmsReceivers",tags = "Sms Receiver APIs")
public class SmsReceiverController {

    @Autowired
    private SmsReceiverService smsReceiverService;

    @GetMapping
    @ApiOperation(value="Select All Sms Receiver",response = Collection.class)
    public Collection<SmsReceiver> getSmsReceivers() {
        return smsReceiverService.getSmsReceivers();
    }

    @PostMapping
    @ApiOperation(value="Create Sms Receiver",response = SmsReceiver.class)
    public SmsReceiver postSmsReceiver(@RequestBody SmsReceiver smsReceiver) {
        smsReceiver.setId(smsReceiverService.getSmsReceivers().size()+1);
        return smsReceiverService.createSmsReceiver(smsReceiver);
    }

    @GetMapping(value="/{id}")
    @ApiOperation(value="Get Sms Receiver By Id Operation",response = SmsReceiver.class)
    public Optional<SmsReceiver> getSmsReceiverById(@PathVariable("id") int id) {
        return smsReceiverService.getSmsReceiverById(id);
    }

    @DeleteMapping(value="/{id}")
    @ApiOperation(value="Delete Sms Receiver",response = SmsReceiver.class)
    public Optional<SmsReceiver> deleteSmsReceiverById(@PathVariable("id") int id) {
        return smsReceiverService.deleteSmsReceiverById(id);
    }

}
