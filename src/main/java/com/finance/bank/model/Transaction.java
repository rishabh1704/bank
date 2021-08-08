package com.finance.bank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String transactionId;
    private Boolean isNotified = false;
    private Date transactionDate;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "account_id")
    private Account account;

    private Double amount;

//    either use transient or mapping
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "to_account_id")
    private Account toAccount;
}
