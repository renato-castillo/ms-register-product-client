package com.bootcamp.msregisterproductclient.dto;

import com.bootcamp.msregisterproductclient.entity.Client;
import com.bootcamp.msregisterproductclient.entity.TypeAccount;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonClientAccountDto {
    private String id;
    private String code;
    private String accountNumber;
    private Float balance;
    private LocalDateTime openingDate;
    private TypeAccount typeAccount;
    private Client client;
    private boolean state;
}
