package com.tuncay.credit.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="creditApplicationReport")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CreditApplicationReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "salary")
    private int salary;

    @Column(name = "credit_score")
    private int credit_score;

    @Column(name = "credit_limit")
    private int credit_limit;

    @Column(name = "credit_result")
    @Enumerated(EnumType.STRING)
    private CreditResult credit_result;

    @Column(name = "message",length = 150)
    private String message;

    @JoinColumn(name = "assignee_user_id")
    @ManyToOne(optional = false,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Account account;


}
