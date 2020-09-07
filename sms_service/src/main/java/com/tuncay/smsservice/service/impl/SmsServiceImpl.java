package com.tuncay.smsservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuncay.smsservice.domain.AccountDTO;
import com.tuncay.smsservice.domain.CreditSmsMessage;
import com.tuncay.smsservice.entity.SmsReceiver;
import com.tuncay.smsservice.service.SmsReceiverService;
import com.tuncay.smsservice.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;


@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private SmsReceiverService smsReceiverService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.credit.client.service.name}")
    private String creditClientService;

    @Override
    public void sentSmsMessage(String smsInfo){
        try{
            CreditSmsMessage creditSmsMessage = objectMapper.readValue(smsInfo, CreditSmsMessage.class);

            // Take account info by using identity number
            AccountDTO accountInfo = getAccountInfo(creditSmsMessage.getIdentity_number());
            SmsReceiver receiver = new SmsReceiver();
            receiver.setId(smsReceiverService.getSmsReceivers().size()+1);
            receiver.setKimlikNumarasi(accountInfo.getIdentityNumber());
            receiver.setAdi(accountInfo.getFirstName());
            receiver.setSoyadi(accountInfo.getLastName());
            receiver.setSalary(accountInfo.getSalary());
            receiver.setTelefonNumarasi(accountInfo.getPhoneNumber());
            receiver.setKrediLimiti(creditSmsMessage.getLimit());
            receiver.setKrediSonucu(creditSmsMessage.getCreditResult());
            SmsReceiver save = smsReceiverService.createSmsReceiver(receiver);
            System.out.println("SMS YOLLANDI" + save.toString());

        }catch (JsonProcessingException e){
            System.out.println("Hata meydana geldi");
        }
    }

    // Microservisten tc no ile kullanıcı bilgilerini aldık.
    @Override
    public AccountDTO getAccountInfo(String identityNumber){
        AccountDTO accountInfo = null;
        List<ServiceInstance> instances = discoveryClient.getInstances(creditClientService);
        if (instances != null && !instances.isEmpty()) {
            ServiceInstance serviceInstance = instances.get(0);
            String url = serviceInstance.getUri().toString();

            url = url + "/account/" + identityNumber;
            RestTemplate restTemplate = new RestTemplate();
            accountInfo = restTemplate.getForObject(url, AccountDTO.class);
            System.out.println(accountInfo.toString());
        }
        return accountInfo;
    }


}
