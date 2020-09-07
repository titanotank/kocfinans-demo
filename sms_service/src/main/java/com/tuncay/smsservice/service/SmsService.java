package com.tuncay.smsservice.service;

import com.tuncay.smsservice.domain.AccountDTO;

public interface SmsService {

    public void sentSmsMessage(String smsInfo);

    public AccountDTO getAccountInfo(String identityNumber);

}
