package com.bootcamp.msregisterproductclient.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PersonClientAccount extends BaseEntity{
    private String code;
    private String accountNumber;
    private LocalDateTime openingDate;
    private TypeAccount typeAccount;
    private Client client;
    private boolean state;
}
