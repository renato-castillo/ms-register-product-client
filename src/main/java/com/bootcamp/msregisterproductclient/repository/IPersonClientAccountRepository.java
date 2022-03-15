package com.bootcamp.msregisterproductclient.repository;

import com.bootcamp.msregisterproductclient.entity.PersonClientAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonClientAccountRepository extends ReactiveMongoRepository<PersonClientAccount, String> {


}
