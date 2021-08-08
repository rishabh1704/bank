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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "address", cascade = {CascadeType.ALL})
    private Set<User> user = new HashSet<>();

    private String line1;
    private String line2;
    private String city;
    private String state;
    private String pinCode;
}
