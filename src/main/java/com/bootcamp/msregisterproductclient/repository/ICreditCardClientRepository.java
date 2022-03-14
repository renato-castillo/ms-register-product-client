package com.bootcamp.msregisterproductclient.repository;

import com.bootcamp.msregisterproductclient.entity.CreditCardClient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICreditCardClientRepository  extends ReactiveMongoRepository<CreditCardClient,String> {

}
