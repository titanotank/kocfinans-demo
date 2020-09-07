package com.tuncay.credit.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value="Account Object")
public class AccountDTO {

    @NotNull
    @ApiModelProperty(value="Kimlik Numarasi")
    private String identityNumber;

    @NotNull
    @ApiModelProperty(value="Adi")
    private String firstName;

    @NotNull
    @ApiModelProperty(value="Soyadi")
    private String lastName;

    @NotNull
    @ApiModelProperty(value="Maas")
    private Long salary;

    @NotNull
    @ApiModelProperty(value="Telefon Numarasi")
    private String phoneNumber;

}
