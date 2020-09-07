package com.tuncay.credit.service;

import com.tuncay.credit.domain.CreditScoreResult;
import com.tuncay.credit.dto.AccountDTO;
import com.tuncay.credit.dto.CreditResultDTO;
import com.tuncay.credit.entity.Account;
import com.tuncay.credit.entity.CreditResult;

public interface CreditApplicationService {

    public CreditResultDTO makeCreditApplicationDTO(AccountDTO accountDTO);

    public CreditResultDTO makeCreditApplication(Account account);

    public CreditScoreResult getCreditScoreResult(Account account);

    public void createCreditApplicationReportDBRecord(Account account, CreditResult creditResult, int creditSkor, int creditLimit, String message);

    public void sentCreditResultSMSMessage(String identity_number,CreditResult result,int limit);

    public void doesTheCustomerHaveCreditDebt(Account account) throws Exception;



}
