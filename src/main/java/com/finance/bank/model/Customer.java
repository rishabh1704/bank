package com.finance.bank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Customer extends User {

    @OneToMany(mappedBy = "owner")
    private Set<Account> account = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

}
