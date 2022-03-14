package com.bootcamp.msregisterproductclient.repository;

import com.bootcamp.msregisterproductclient.entity.CreditClient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICreditClientRepository  extends ReactiveMongoRepository<CreditClient, String> {

}
