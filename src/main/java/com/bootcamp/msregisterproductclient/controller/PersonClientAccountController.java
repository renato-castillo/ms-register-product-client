package com.bootcamp.msregisterproductclient.controller;

import com.bootcamp.msregisterproductclient.dto.PersonClientAccountDto;
import com.bootcamp.msregisterproductclient.resource.PersonClientAccountResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/account/person")
public class PersonClientAccountController {

    @Autowired
    private PersonClientAccountResource personClientAccountResource;

    @PostMapping
    public Mono<PersonClientAccountDto> create(@RequestBody PersonClientAccountDto personClientAccountDto){
        return personClientAccountResource.create(personClientAccountDto);
    }
    @PutMapping
    public Mono<PersonClientAccountDto> update(@RequestBody PersonClientAccountDto personClientAccountDto){
        return personClientAccountResource.update(personClientAccountDto);
    }

    @GetMapping
    public Flux<PersonClientAccountDto> findAll(){
        return personClientAccountResource.findAll();
    }

    @GetMapping("/document/{document}/type/{type}/account/{account}")
    public Mono<PersonClientAccountDto> findByDocumentAndDocumentTypeAndAccount(@PathVariable String document,
                                                                                @PathVariable String type,
                                                                                @PathVariable String account) {
        return personClientAccountResource.findByDocumentNumberDocumentTypeAndAccountNumber(document, type, account);
    }

    @DeleteMapping
    public Mono<Void> delete(@RequestBody PersonClientAccountDto personClientAccountDto){
        return personClientAccountResource.delete(personClientAccountDto);
    }
}
