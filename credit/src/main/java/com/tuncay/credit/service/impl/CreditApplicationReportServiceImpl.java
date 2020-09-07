package com.tuncay.credit.service.impl;

import com.tuncay.credit.entity.Account;
import com.tuncay.credit.entity.CreditApplicationReport;
import com.tuncay.credit.repo.CreditApplicationReportRepository;
import com.tuncay.credit.service.CreditApplicationReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CreditApplicationReportServiceImpl implements CreditApplicationReportService {

    @Autowired
    private CreditApplicationReportRepository reportRepository;

    @Override
    public CreditApplicationReport save(CreditApplicationReport creditApplicationReport) {
        log.info("Kredi basvuru raporu kaydediliyor, Rapor: " + creditApplicationReport.toString());
        return reportRepository.save(creditApplicationReport);
    }

    @Override
    public List<CreditApplicationReport> getAllCreditApplicationReportByAccount(Account account) {
        log.info("Musterinin daha onceki tüm kredi basvurulari getiriliyor: Hesap : " + account.toString());
        return reportRepository.getAllByAccount(account);
    }
}
