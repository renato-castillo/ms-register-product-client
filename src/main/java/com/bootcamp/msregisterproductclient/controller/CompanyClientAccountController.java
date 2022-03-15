package com.bootcamp.msregisterproductclient.controller;

import com.bootcamp.msregisterproductclient.dto.CompanyClientAccountDto;
import com.bootcamp.msregisterproductclient.dto.CreditCardClientDto;
import com.bootcamp.msregisterproductclient.dto.PersonClientAccountDto;
import com.bootcamp.msregisterproductclient.resource.CompanyClientAccountResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/account/company")
public class CompanyClientAccountController {

    @Autowired
    private CompanyClientAccountResource companyClientAccountResource;

    @PostMapping
    public Mono<CompanyClientAccountDto> create(@RequestBody CompanyClientAccountDto companyClientAccountDto){
        return companyClientAccountResource.create(companyClientAccountDto);
    }
    @PutMapping
    public Mono<CompanyClientAccountDto> update(@RequestBody CompanyClientAccountDto companyClientAccountDto){
        return companyClientAccountResource.update(companyClientAccountDto);
    }

    @GetMapping
    public Flux<CompanyClientAccountDto> findAll(){
        return companyClientAccountResource.findAll();
    }

    @GetMapping("/document/{document}/type/{type}/account/{account}")
    public Mono<CompanyClientAccountDto> findByDocumentAndDocumentTypeAndAccount(@PathVariable String document,
                                                                                @PathVariable String type,
                                                                                @PathVariable String account) {
        return companyClientAccountResource.findByDocumentNumberAndDocumentTypeAndAccount(document, type, account);
    }

    @DeleteMapping
    public Mono<Void> delete(@RequestBody CompanyClientAccountDto companyClientAccountDto){
        return companyClientAccountResource.delete(companyClientAccountDto);
    }
}
