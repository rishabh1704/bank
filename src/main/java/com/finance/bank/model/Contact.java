package com.finance.bank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
//@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "email" , "phone"}) })
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "contact")
    private User user;

    private String email;
    private String phone;
}
