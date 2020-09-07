package com.tuncay.credit.dto;

import com.tuncay.credit.entity.CreditResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="Credit Result Object")
public class CreditResultDTO {

    @ApiModelProperty(value="creditResult")
    private CreditResult creditResult;
    @ApiModelProperty(value="limit")
    private int limit;
    @ApiModelProperty(value="message")
    private String message;

}
