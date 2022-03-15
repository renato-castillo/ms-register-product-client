package com.bootcamp.msregisterproductclient.service;

import com.bootcamp.msregisterproductclient.entity.CompanyClientAccount;
import com.bootcamp.msregisterproductclient.repository.ICompanyClientAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CompanyClientAccountServiceImpl implements ICompanyClientAccountService{

    @Autowired
    ICompanyClientAccountRepository iCompanyClientAccountRepository;

    @Override
    public Mono<CompanyClientAccount> save(CompanyClientAccount companyClientAccount) {
        return iCompanyClientAccountRepository.save(companyClientAccount);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return iCompanyClientAccountRepository.deleteById(id);
    }

    @Override
    public Mono<CompanyClientAccount> findById(String id) {
        return iCompanyClientAccountRepository.findById(id);
    }

    @Override
    public Flux<CompanyClientAccount> findAll() {
        return iCompanyClientAccountRepository.findAll();
    }


    @Override
    public Mono<CompanyClientAccount> findByDocumentNumberAndDocumentTypeAndAccount(String document, String documentType, String account) {
        return iCompanyClientAccountRepository.findCompanyClientAccountByClient_NumberDocumentAndClient_DocumentTypeAndAccountNumber(document, documentType, account);
    }
}
