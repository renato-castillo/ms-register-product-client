package com.bootcamp.msregisterproductclient.service;

import com.bootcamp.msregisterproductclient.entity.CreditClient;
import com.bootcamp.msregisterproductclient.repository.ICreditClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditClientServiceImpl implements ICreditClientService{

    @Autowired
    ICreditClientRepository iCreditClientRepository;
    @Override
    public Mono<CreditClient> save(CreditClient creditClient) {
        return iCreditClientRepository.save(creditClient);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return iCreditClientRepository.deleteById(id);
    }

    @Override
    public Mono<CreditClient> findById(String id) {
        return iCreditClientRepository.findById(id);
    }

    @Override
    public Flux<CreditClient> findAll() {
        return iCreditClientRepository.findAll();
    }


}
