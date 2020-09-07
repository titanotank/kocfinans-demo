package com.tuncay.credit.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CreditScoreResult {

    private String identity_number;
    private int credit_score;

}
