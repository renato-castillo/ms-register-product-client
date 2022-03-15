package com.bootcamp.msregisterproductclient.service;

import com.bootcamp.msregisterproductclient.entity.PersonClientAccount;
import com.bootcamp.msregisterproductclient.util.ICrud;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

public interface IPersonClientAccountService  extends ICrud<PersonClientAccount, String> {

    Mono<PersonClientAccount> findByDocumentNumberAndDocumentTypeAndAccountNumber(String documentNumber,
                                                                                  String documentType,
                                                                                  String accountNumber);
}
