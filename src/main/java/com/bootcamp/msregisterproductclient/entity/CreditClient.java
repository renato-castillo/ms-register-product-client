package com.bootcamp.msregisterproductclient.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditClient extends BaseEntity{
    private String code;
    private Float  creditAmount;
    private Float interestRate;
    private Client client;
    private CreditType creditType;
    private BankAccount bankAccount;
    private boolean state;
}
