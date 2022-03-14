package com.bootcamp.msregisterproductclient.repository;

import com.bootcamp.msregisterproductclient.entity.CompanyClientAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompanyClientAccountRepository extends ReactiveMongoRepository<CompanyClientAccount, String> {

}
