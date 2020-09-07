package com.tuncay.smsservice.domain;


import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AccountDTO {

    private String identityNumber;
    private String firstName;
    private String lastName;
    private int salary;
    private String phoneNumber;

}
