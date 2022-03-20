package com.bootcamp.msregisterproductclient.dto;

import com.bootcamp.msregisterproductclient.entity.PersonClientAccount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyBalanceDto {
	private Float balanceDaily;
    private PersonClientAccount personClientAccount; 
}
