package com.tuncay.credit.service;

import com.tuncay.credit.entity.Account;
import com.tuncay.credit.entity.CreditApplicationReport;

import java.util.List;

public interface CreditApplicationReportService {

    public CreditApplicationReport save(CreditApplicationReport creditApplicationReport);

    List<CreditApplicationReport> getAllCreditApplicationReportByAccount(Account account);

}
