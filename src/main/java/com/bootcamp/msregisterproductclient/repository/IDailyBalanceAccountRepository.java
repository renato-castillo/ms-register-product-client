package com.bootcamp.msregisterproductclient.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.msregisterproductclient.entity.DailyBalanceAccount;

@Repository
public interface IDailyBalanceAccountRepository extends ReactiveMongoRepository<DailyBalanceAccount, String>{

}
