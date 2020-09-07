package com.tuncay.credit.service.impl;

import com.tuncay.credit.dto.AccountDTO;
import com.tuncay.credit.entity.Account;
import com.tuncay.credit.repo.AccountRepository;
import com.tuncay.credit.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Account getAccountByIdentityNumber(String identityNumber) {
        log.info("Müsteri Bilgisi veriliyor : TC" + identityNumber);
        return accountRepository.getByIdentityNumber(identityNumber);
    }

    @Override
    public Account save(Account account) {
        log.info("Müsteri Bilgisi kaydediliyor,Müsteri : " + account.toString());
        return accountRepository.save(account);
    }

    //  Daha önceden kayıtlı müşterinin bilgileri uyusuyor mu ?
    public void verifyAccount(Account account, Account recordedAccount) throws Exception{
        log.info("Müsteri Bilgisi kontrol ediliyor ,Müsteri : " + account.toString());
        if(!account.getFirstName().equals(recordedAccount.getFirstName())){
            log.info("Aynı TC no ile kayitli müsterinin adi hatali ,Müsteri : " + account.toString());
            throw new Exception("Aynı TC no ile kayitli müsterinin adi hatali: TC " + account.getIdentityNumber());
        }
        if(!account.getLastName().equals(recordedAccount.getLastName())){
            log.info("Aynı TC no ile kayitli müsterinin soyadi hatali ,Müsteri : " + account.toString());
            throw new Exception("Aynı TC no ile kayitli müsterinin soyadi hatali: TC " + account.getIdentityNumber());
        }
        if(!account.getPhoneNumber().equals(recordedAccount.getPhoneNumber())){
            log.info("Aynı TC no ile kayitli müsterinin telefonu hatali ,Müsteri : " + account.toString());
            throw new Exception("Aynı TC no ile kayitli müsterinin telefonu hatali: TC " + account.getIdentityNumber());
        }
    }

    @Override
    public AccountDTO getAccountDTOByIdentityNumber(String identityNumber) {
        log.info("Aynı TC no ile müsteri alınıyor  TC: " + identityNumber);
        Account account = accountRepository.getByIdentityNumber(identityNumber);
        AccountDTO accountDto = modelMapper.map(account, AccountDTO.class);
        log.info("Aynı TC no ile müsteri alindi geri return ediliyor  TC: " + identityNumber);
        return accountDto;
    }

    // Kayıtlı bir müşterimi
    @Override
    public Account isRecordedAccount(Account account){
        Account recordedAccount = accountRepository.getByIdentityNumber(account.getIdentityNumber());
        log.info("Aynı TC no ile kayıtlı eski müsteri aranıyor  TC: " + account.getIdentityNumber());
        if(recordedAccount == null){  // tckimlik ile kayıtlı müşteri yok
            log.info("Aynı TC no ile kayıtlı eski müsteri bulunamadı  TC: " + account.getIdentityNumber());
            return null;
        }
        log.info("Aynı TC no ile kayıtlı eski müsteri bulundu geri dönülüyor TC: " + account.getIdentityNumber());
        return recordedAccount;
    }

    // Girilen TC kimlik numarası geçerli mi
    @Override
    public Boolean verificateIdentity_Number(String id) {
        log.info("TC numarası kontrol ediliyor TC: " + id);
        if (id == null) return false;

        if (id.length() != 11) return false;

        char[] chars = id.toCharArray();
        int[] a = new int[11];

        for(int i=0; i<11; i++) {
            a[i] = chars[i] - '0';
        }

        if(a[0] == 0) return false;
        if(a[10] % 2 == 1) return false;

        if(((a[0] + a[2] + a[4] + a[6] + a[8]) * 7 - (a[1] + a[3] + a[5] + a[7])) % 10 != a[9]) return false;

        if((a[0] + a[1] + a[2] + a[3] + a[4] + a[5] + a[6] + a[7] + a[8] + a[9]) % 10 != a[10]) return false;
        log.info("TC numarası basarıyla kontrol edildi TC: " + id);
        return true;
    }


}
