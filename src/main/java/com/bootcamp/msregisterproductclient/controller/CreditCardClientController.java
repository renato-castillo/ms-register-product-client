package com.bootcamp.msregisterproductclient.controller;

import com.bootcamp.msregisterproductclient.dto.CreditCardClientDto;
import com.bootcamp.msregisterproductclient.resource.CreditCardClientResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/credit-card/client")
public class CreditCardClientController {
    @Autowired
    private CreditCardClientResource creditCardClientResource;

   @PostMapping
    public Mono<CreditCardClientDto> create(@RequestBody CreditCardClientDto creditCardClientDto){
        return creditCardClientResource.create(creditCardClientDto);
    }
    @PutMapping
    public Mono<CreditCardClientDto> update(@RequestBody CreditCardClientDto creditCardClientDto){
        return creditCardClientResource.update(creditCardClientDto);
    }

    @GetMapping
    public Flux<CreditCardClientDto> findAll(){
        return creditCardClientResource.findAll();
    }

    @DeleteMapping
    public Mono<Void> delete(@RequestBody CreditCardClientDto creditCardClientDto){
        return creditCardClientResource.delete(creditCardClientDto);
    }
}
