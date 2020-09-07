package com.tuncay.credit.service.impl;

import com.tuncay.credit.domain.CreditScoreResult;
import com.tuncay.credit.domain.CreditSmsMessage;
import com.tuncay.credit.dto.AccountDTO;
import com.tuncay.credit.dto.CreditResultDTO;
import com.tuncay.credit.entity.Account;
import com.tuncay.credit.entity.CreditApplicationReport;
import com.tuncay.credit.entity.CreditResult;
import com.tuncay.credit.helper.KafkaProducerHelper;
import com.tuncay.credit.service.AccountService;
import com.tuncay.credit.service.CreditApplicationReportService;
import com.tuncay.credit.service.CreditApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class CreditApplicationServiceImp implements CreditApplicationService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CreditApplicationReportService reportService;

    @Autowired
    private KafkaProducerHelper kafkaProducer;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${spring.credit.score.service.name}")
    private String creditScoreService;

    private int credit_score = 0;

    @Override
    public CreditResultDTO makeCreditApplicationDTO(AccountDTO accountDTO){
        log.info("Form bilgisi isleniyor : " + accountDTO.toString());
        Account account = modelMapper.map(accountDTO, Account.class);
        account.setCreatedAt(new Date());
        account.setCreatedBy("CreditApplicationService");
        log.info("Form bilgisi islendi bavrusu yapılıyor : " + accountDTO.toString());
        return makeCreditApplication(account);
    }

    @Override
    @Transactional
    public CreditResultDTO makeCreditApplication(Account account) {

        CreditResult creditResult = null;
        Account recordedAccount;

        int salary = 0;
        int credit_limit = 0;
        int credit_limit_multiplier = 4;
        String message = null;                         //Hata veya sonuc mesajlarını tasısın

        // Kredi verme işlemleri:
        // ONAY (credi limiti),RED, hatalı durumlar hesaplanır.
        try{
            // VERIFICATION STEP ------------------------------------------------------> ( istenmedi ama ekledim )

            // Girilen TC geçerli değilse otomatik RED ve nedeni
            log.info("Kullanıcı kimligi kontrol ediliyor : " + account.getIdentityNumber());
            boolean verifyIdentity = accountService.verificateIdentity_Number(account.getIdentityNumber());
            if(!verifyIdentity){
                creditResult = CreditResult.RED;
                log.info("Basvuru red ediliyor TC NO HATALI : " + account.getIdentityNumber());
                throw new Exception("TC no hatali");
            }


            // Müşteri kayıtlıysa bilgiler eslesiyor mu ve borcu var mı ?
            recordedAccount = accountService.isRecordedAccount(account);
            if(recordedAccount != null){
                accountService.verifyAccount(account,recordedAccount); // Daha önceki kayıtla datalar uyusuyormu ?
                account = recordedAccount;
                doesTheCustomerHaveCreditDebt(recordedAccount);       // Onceden ödemediği borcu varmı ?  Aynı müsteriye 1 sn sonra kredi vermeyelim die
            }

            // KREDI VERME KARARININ ALINDIĞI STEP ------------------------------------------------------
            log.info("Kredi skoru alınıyor: TC: " + account.getIdentityNumber());
            credit_score = getCreditScoreResult(account).getCredit_score();   // id ve score microservisten alınıyor
            log.info("Kredi skoru alındı: skor: " + credit_score);
            salary = account.getSalary();

            if(credit_score < 500){
                creditResult = CreditResult.RED;
                log.info("Kredi skoru yetersiz. Kredi skoru: " + credit_score);
                throw new Exception("Kredi skoru yetersiz. Kredi skoru: " + credit_score);
            }
            else if(credit_score >= 500 && credit_score < 1000){
                creditResult = CreditResult.ONAY;

                // 5.000 * 2 (kredi limit çarpanı) = 10.000 TL limit ata
                if(salary < 5000){
                    credit_limit = 10000;
                    log.info("Kredi alabilir limit :  " + credit_limit + " TL.");
                }
                // GELİR BİLGİSİ * 3 (5000 TL üzeri müşteriler için kredi limiti çarpanı 3 alındı)
                else{
                    credit_limit_multiplier = 3;
                    credit_limit = salary * credit_limit_multiplier;
                    log.info("Kredi alabilir limit :  " + credit_limit + " TL.");
                }

            }
            else{
                creditResult = CreditResult.ONAY;
                credit_limit = salary * credit_limit_multiplier;
                log.info("Kredi alabilir limit :  " + credit_limit + " TL.");
            }

            message = "Kredi sonucu Onaylandi";

        }catch (Exception e){
            creditResult = CreditResult.RED;
            message ="Kredi basvursu reddedildi: " + e.getMessage();
            log.info("Kredi basvursu reddedildi: " + e.getMessage());
        }



        //DBYE KAYIT STEP --------------------------------------------------------------------------------------------
        if(accountService.isRecordedAccount(account) != null ){  //account tablosunda kayıtlı müsteri ise,
            account = accountService.isRecordedAccount(account); //rapor tablosuna kaydederken kullansın
        }
        log.info("Kredi basvuru raporu isleniyor");
        createCreditApplicationReportDBRecord(account,creditResult,credit_score,credit_limit,message);
        log.info("Kredi basvuru raporu islendi");


        //SMS YOLLAMASI ICIN sentSms metoduyla KAFKAYA GÖNDERİLDİ-------------------------------------------------------------------
        log.info("Müsteriye Sms Yollaniyor");
        sentCreditResultSMSMessage(account.getIdentityNumber(),creditResult,credit_limit);
        log.info("Müsteriye Sms Yollandi");

        log.info("Basvuru sonucu alindi geri dönülüyor : " + creditResult);
        return new CreditResultDTO(creditResult,(int) credit_limit,message);
    }


    // Microservisten geldi
    @Override
    public CreditScoreResult getCreditScoreResult(Account account) {
        CreditScoreResult result = null;
        log.info("Microservisten credi skoru alınıyor : ");
        List<ServiceInstance> instances = discoveryClient.getInstances(creditScoreService);
        if (instances != null && !instances.isEmpty()) {
            ServiceInstance serviceInstance = instances.get(0);
            String url = serviceInstance.getUri().toString();
            url = url + "/creditScore/" + account.getIdentityNumber();
            RestTemplate restTemplate = new RestTemplate();
            result = restTemplate.getForObject(url, CreditScoreResult.class);
            System.out.println(result.toString());
        }
        log.info("Microservisten credi skoru alındı : ");
        return result;
    }


    @Override
    @Transactional
    public void createCreditApplicationReportDBRecord(Account account, CreditResult creditResult, int creditSkore, int creditLimit, String message) {
        log.info("Kredi raporu oluşturuyor : ");
        CreditApplicationReport creditApplicationReport = new CreditApplicationReport();
        creditApplicationReport.setAccount(account);
        creditApplicationReport.setSalary(account.getSalary());
        creditApplicationReport.setCredit_result(creditResult);
        creditApplicationReport.setCredit_score(creditSkore);
        creditApplicationReport.setCredit_limit(creditLimit);
        creditApplicationReport.setMessage(message);
        creditApplicationReport.setCreatedAt(new Date());
        creditApplicationReport.setCreatedBy(account.getFirstName());
        log.info("Kredi raporu oluşturuldu : ");
        reportService.save(creditApplicationReport);
        log.info("Kredi raporu kaydedildi : ");
    }


    // Kafka Topigine gerekli bilgiler yollanır
    public void sentCreditResultSMSMessage(String identity_number,CreditResult result,int limit){
        log.info("Sms bilgisi kafka topigine yollanıyor : ");
        CreditSmsMessage kafkaSmsMessage = new CreditSmsMessage();
        kafkaSmsMessage.setIdentity_number(identity_number);
        kafkaSmsMessage.setCreditResult(result);
        kafkaSmsMessage.setLimit(limit);
        try{
            kafkaProducer.sentSmsMessageToKafkaTopic(kafkaSmsMessage);
        }catch (Exception e){
            log.info("SMS YOLLANAMADI TC: " + identity_number);
            System.out.println("SMS YOLLANAMADI TC: " + identity_number);
        }
        log.info("SMS YOLLANDI TC: " + identity_number);

    }


    // Müşterinin bize kredi borcu var mı bakılır
    public void doesTheCustomerHaveCreditDebt(Account account) throws Exception {
        List<CreditApplicationReport> allReports = reportService.getAllCreditApplicationReportByAccount(account);
        if(allReports == null) return;

        for(CreditApplicationReport report : allReports){
            if(report.getCredit_result() != CreditResult.ODENDI){
                log.info("TC no ile kayitli müşterinin daha onceden odemedigi kredi bulunuyor. TC: " + account.getIdentityNumber());
                throw new Exception("TC no ile kayitli müşterinin daha onceden odemedigi kredi bulunuyor. TC: " + account.getIdentityNumber());
            }
        }
        return;
    }




}
