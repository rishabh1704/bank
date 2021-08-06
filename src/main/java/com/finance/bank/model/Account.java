package com.finance.bank.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer owner;

    private String accountNumber;
    private String accountStatus;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private Double balance;

    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions;

}
