package com.bootcamp.msregisterproductclient.service;

import com.bootcamp.msregisterproductclient.entity.CreditCardClient;
import com.bootcamp.msregisterproductclient.repository.ICreditCardClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditCardClientServiceImpl implements ICreditCardClientService{

    @Autowired
    ICreditCardClientRepository iCreditCardClientRepository;

    @Override
    public Mono<CreditCardClient> save(CreditCardClient creditCardClient) {
        return iCreditCardClientRepository.save(creditCardClient);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return iCreditCardClientRepository.deleteById(id);
    }

    @Override
    public Mono<CreditCardClient> findById(String id) {
        return iCreditCardClientRepository.findById(id);
    }

    @Override
    public Flux<CreditCardClient> findAll() {
        return iCreditCardClientRepository.findAll();
    }


}
