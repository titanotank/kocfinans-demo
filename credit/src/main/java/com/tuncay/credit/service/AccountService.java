package com.tuncay.credit.service;


import com.tuncay.credit.dto.AccountDTO;
import com.tuncay.credit.entity.Account;

public interface AccountService {

    public Account getAccountByIdentityNumber(String identityNumber);

    public Account save(Account account);

    public Boolean verificateIdentity_Number(String identityNumber);

    public Account isRecordedAccount(Account account);

    public void verifyAccount(Account account, Account recordedAccount) throws Exception;

    public AccountDTO getAccountDTOByIdentityNumber(String identityNumber);

}
