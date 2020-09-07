package com.tuncay.creditscore.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@ApiModel(value="Credit Score Result Object")
public class CreditScoreResult {

    @ApiModelProperty(value="identity number")
    private String identity_number;
    @ApiModelProperty(value="credit score")
    private int credit_score;

}
