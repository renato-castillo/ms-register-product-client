package com.bootcamp.msregisterproductclient.service;

import com.bootcamp.msregisterproductclient.entity.PersonClientAccount;
import com.bootcamp.msregisterproductclient.repository.IPersonClientAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class PersonClientAccountServiceImpl implements IPersonClientAccountService{

    @Autowired
    IPersonClientAccountRepository iPersonClientAccountRepository;
    @Override
    public Mono<PersonClientAccount> save(PersonClientAccount personClientAccount) {
        return iPersonClientAccountRepository.save(personClientAccount);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return iPersonClientAccountRepository.deleteById(id);
    }

    @Override
    public Mono<PersonClientAccount> findById(String id) {
        return iPersonClientAccountRepository.findById(id);
    }

    @Override
    public Flux<PersonClientAccount> findAll() {
        return iPersonClientAccountRepository.findAll();
    }

    @Override
    public Mono<PersonClientAccount> findByDocumentNumberAndDocumentTypeAndAccountNumber(String documentNumber, String documentType, String accountNumber) {
        return iPersonClientAccountRepository.findPersonClientAccountByClient_NumberDocumentAndClient_DocumentTypeAndAccountNumber(documentNumber,
                documentType, accountNumber);
    }

    @Override
    public Flux<PersonClientAccount> findByDocumentNumberAndDocumentTypeAndTypeAccountName(String documentNumber, String documentType, String typeAccountName) {
        return iPersonClientAccountRepository.findPersonClientAccountByClient_NumberDocumentAndClient_DocumentTypeAndTypeAccount_NameAndStateIsTrue(documentNumber,
                documentType, typeAccountName);
    }
}
