package com.bootcamp.msregisterproductclient.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CompanyClientAccount extends BaseEntity{
    private String code;
    private String accountNumber;
    private LocalDateTime openingDate;
    private TypeAccount typeAccount;
    private Client client;
    private List<Responsible> responsible;
}
