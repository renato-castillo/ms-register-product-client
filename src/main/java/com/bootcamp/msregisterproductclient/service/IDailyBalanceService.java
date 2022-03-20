package com.bootcamp.msregisterproductclient.service;

import com.bootcamp.msregisterproductclient.entity.CreditClient;
import com.bootcamp.msregisterproductclient.entity.DailyBalanceAccount;
import com.bootcamp.msregisterproductclient.util.ICrud;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IDailyBalanceService extends ICrud<DailyBalanceAccount, String>{
}
