package com.tuncay.credit.repo;

import com.tuncay.credit.entity.Account;
import com.tuncay.credit.entity.CreditApplicationReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditApplicationReportRepository extends JpaRepository<CreditApplicationReport,Long> {

    List<CreditApplicationReport> getAllByAccount(Account account);


}
