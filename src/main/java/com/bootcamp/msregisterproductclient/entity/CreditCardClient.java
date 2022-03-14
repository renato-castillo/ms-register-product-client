package com.bootcamp.msregisterproductclient.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreditCardClient  extends BaseEntity{
    private String code;
    private Float amountLimit;
    private Float interest;
    private LocalDateTime openingDate;
    private LocalDateTime deliveryDate;
    private String state;
    private Client client;
    private CreditCard creditCard;
}
