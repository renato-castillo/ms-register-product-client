package com.bootcamp.msregisterproductclient.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document
public class DailyBalanceAccount extends BaseEntity{
	private Float balanceDaily;
    private PersonClientAccount personClientAccount;   
}
