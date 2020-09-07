package com.tuncay.smsservice.entity;

import com.tuncay.smsservice.domain.CreditResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="SmsReceiver")
@ApiModel(value="Sms Receiver Mongo db record Object")
public class SmsReceiver {

    @Id
    @ApiModelProperty(value="Record Id")
    private Integer id;
    @ApiModelProperty(value="TC No")
    private String kimlikNumarasi;
    @ApiModelProperty(value="Adi")
    private String adi;
    @ApiModelProperty(value="Soyadi")
    private String soyadi;
    @ApiModelProperty(value="Maas")
    private Integer salary;
    @ApiModelProperty(value="Kredi Sonucu")
    private CreditResult krediSonucu;
    @ApiModelProperty(value="Kredi Limit")
    private Integer krediLimiti;
    @ApiModelProperty(value="Telefon Numarası")
    private String telefonNumarasi;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKimlikNumarasi() {
        return kimlikNumarasi;
    }

    public void setKimlikNumarasi(String kimlikNumarasi) {
        this.kimlikNumarasi = kimlikNumarasi;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getSoyadi() {
        return soyadi;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public CreditResult getKrediSonucu() {
        return krediSonucu;
    }

    public void setKrediSonucu(CreditResult krediSonucu) {
        this.krediSonucu = krediSonucu;
    }

    public Integer getKrediLimiti() {
        return krediLimiti;
    }

    public void setKrediLimiti(Integer krediLimiti) {
        this.krediLimiti = krediLimiti;
    }

    public String getTelefonNumarasi() {
        return telefonNumarasi;
    }

    public void setTelefonNumarasi(String telefonNumarasi) {
        this.telefonNumarasi = telefonNumarasi;
    }
}
