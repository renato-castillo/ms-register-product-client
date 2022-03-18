package com.bootcamp.msregisterproductclient.resource;

import com.bootcamp.msregisterproductclient.dto.PersonClientAccountDto;
import com.bootcamp.msregisterproductclient.entity.PersonClientAccount;
import com.bootcamp.msregisterproductclient.entity.TypeAccount;
import com.bootcamp.msregisterproductclient.exception.GenericException;
import com.bootcamp.msregisterproductclient.exception.ModelNotFoundException;
import com.bootcamp.msregisterproductclient.service.IPersonClientAccountService;
import com.bootcamp.msregisterproductclient.util.AccountValidationCreationService;
import com.bootcamp.msregisterproductclient.util.MapperUtil;
import com.bootcamp.msregisterproductclient.webclient.dto.PersonalAccountDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PersonClientAccountResource extends MapperUtil {

    @Autowired
    private IPersonClientAccountService iPersonClientAccountService;

    @Autowired
    private AccountValidationCreationService accountValidationCreationService;

    public Mono<PersonClientAccountDto> create(PersonClientAccountDto personClientAccountDto){
        PersonClientAccount personClientAccount = map(personClientAccountDto,PersonClientAccount.class);
        personClientAccount.setId(new ObjectId().toString());
        personClientAccount.setCreatedAt(LocalDateTime.now());
        personClientAccount.setAccountNumber(UUID.randomUUID().toString());

        if(personClientAccountDto.getClient().getClientType().equalsIgnoreCase("PERSONAL")) {
            return accountValidationCreationService.validate(personClientAccountDto).flatMap(x -> {
                        personClientAccount.setTypeAccount(new TypeAccount(x.getName(), x.getMaxMonthlyMovements()));
                        return iPersonClientAccountService.save(personClientAccount).map(y -> map(y, PersonClientAccountDto.class));
                    }).onErrorResume(Mono::error);
        }

        return Mono.error(new GenericException("Client Type Not Supported"));

    }

    public Flux<PersonClientAccountDto> findAll(){
        return iPersonClientAccountService.findAll().map(x->map(x,PersonClientAccountDto.class));
    }

    public Mono<PersonClientAccountDto> update(PersonClientAccountDto personClientAccountDto){
        return iPersonClientAccountService.findById(personClientAccountDto.getId())
                .switchIfEmpty(Mono.error(new ModelNotFoundException()))
                .flatMap(p->{
                    PersonClientAccount personClientAccount =  map(personClientAccountDto,PersonClientAccount.class);
                    personClientAccount.setUpdatedAt(LocalDateTime.now());
                    return iPersonClientAccountService.save(personClientAccount).map(y->map(y,PersonClientAccountDto.class));
                });
    }

    public Mono<PersonClientAccountDto> findById(String id){
        return iPersonClientAccountService.findById(id)
                .switchIfEmpty(Mono.error(new ModelNotFoundException()))
                .map(x-> map(x,PersonClientAccountDto.class));
    }

    public Mono<PersonClientAccountDto> findByDocumentNumberDocumentTypeAndAccountNumber(String documentNumber,
                                                                                         String documentType,
                                                                                         String accountNumber) {
        return iPersonClientAccountService.findByDocumentNumberAndDocumentTypeAndAccountNumber(documentNumber,
                documentType,accountNumber)
                .switchIfEmpty(Mono.error(new ModelNotFoundException()))
                .map(x -> map(x, PersonClientAccountDto.class));
    }

    public Mono<Void> delete(PersonClientAccountDto personClientAccountDto)
    {
        return iPersonClientAccountService.findById(personClientAccountDto.getId())
                .switchIfEmpty(Mono.error(new ModelNotFoundException()))
                .flatMap(x-> iPersonClientAccountService.deleteById(personClientAccountDto.getId()));
    }
}
