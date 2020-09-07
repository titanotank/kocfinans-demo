package com.tuncay.credit.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="customer",indexes = @Index(name="IX_identity_number",columnList = "identity_number",unique = true))
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="identity_number",length = 100,unique = true)
    private String identityNumber;
    @Column(name="first_name",length = 100)
    private String firstName;
    @Column(name="last_name",length = 100)
    private String lastName;
    @Column(name="salary")
    private int salary;
    @Column(name="phone_number",length = 20)
    private String phoneNumber;

    @JoinColumn(name = "assignee_user_reports")
    @OneToMany(fetch = FetchType.LAZY)
    private List<CreditApplicationReport> reports;

}
